FROM maven:3.6.0-jdk-8-alpine
ADD . /app
WORKDIR /app
RUN mvn install:install-file -Dfile=dependencies/jpHash-1.0-SNAPSHOT.jar -DgroupId=com.pragone -DartifactId=jpHash -Dpackaging=jar -Dversion=1.0-SNAPSHOT
RUN mvn install:install-file -Dfile=dependencies/sa-jdi.jar -DgroupId=sun.jvm.hotspot -DartifactId=sa-jdi -Dpackaging=jar -Dversion=1.0
RUN mvn clean compile
RUN mkdir -p /app/opencv-2.4.9/build/
WORKDIR /app/opencv-2.4.9/build/
RUN echo -e '@edge http://nl.alpinelinux.org/alpine/edge/main\n@testing http://nl.alpinelinux.org/alpine/edge/testing' >> /etc/apk/repositories
RUN apk update
RUN apk add --update g++ gcc cmake make linux-headers libc-dev clang-dev clang alpine-sdk musl-dev openblas-dev \
                                        libjpeg libjpeg-turbo-dev libpng-dev jasper-dev tiff-dev libwebp-dev \
                                        pkgconf gsl libavc1394-dev libdc1394-dev \
                                        libtbb@testing libtbb-dev@testing \
                                        python python-dev py-pip py-numpy-dev@testing apache-ant
RUN cmake -D CMAKE_BUILD_TYPE=RELEASE -D CMAKE_INSTALL_PREFIX=/usr/local -D WITH_1394=false -D WITH_AVFOUNDATION=false -D WITH_CARBON=false -D WITH_CUDA=false -D WITH_VT=false -D WITH_CUFFT=false -D WITH_CUBLAS=false -D WITH_NVCUVID=false -D WITH_VFW=false -D WITH_FFMPEG=false -D WITH_GSTREAMER=false -D WITH_GSTREAMER_0_10=false -D WITH_GTK=false -D WITH_IMAGEIO=true -D WITH_JASPER=false -D WITH_JPEG=false -D WITH_OPENEXR=false -D WITH_OPENGL=false -D WITH_OPENNI=false -D WITH_PNG=true -D WITH_PVAPI=false -D WITH_GIGEAPI=false -D WITH_QT=false -D WITH_WIN32UI=false -D WITH_QUICKTIME=false -D WITH_CSTRIPES=false -D WITH_TIFF=false -D WITH_UNICAP=false -D WITH_V4L=false -D WITH_LIBV4L=false -D WITH_XIMEA=false -D WITH_XINE=false -D WITH_OPENCL=false -D WITH_OPENCLAMDFFT=false -D WITH_OPENCLAMDBLAS=false -D WITH_INTELPERC=false -D BUILD_opencv_apps=false -D BUILD_ANDROID_EXAMPLES=false -D BUILD_DOCS=false -D BUILD_EXAMPLES=false -D BUILD_PERF_TESTS=false -D BUILD_TESTS=false -D BUILD_WITH_DEBUG_INFO=false -D BUILD_FAT_JAVA_LIB=false -D BUILD_ANDROID_SERVICE=false -D BUILD_ANDROID_PACKAGE=false -D BUILD_TINY_GPU_MODULE=false -D BUILD_ZLIB=false -D BUILD_TIFF=false -D BUILD_JASPER=false -D BUILD_JPEG=false -D BUILD_OPENEXR=false -DENABLE_PRECOMPILED_HEADERS=OFF -D WITH_IPP=no -D WITH_OPENEXR=no -D BUILD_SHARED_LIBRARIES=OFF ..
RUN ln -s /usr/local/share/OpenCV/java/libopencv_java249.so /usr/lib/
RUN make
RUN make install
WORKDIR /app
CMD ["mvn", "test"]
