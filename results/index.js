(function () {
    var Navigator = function (file, screenshots, container, cursor, f) {
        var file = file,
            elements = [],
            count = 0,
            paintPosition,
            filename = f;

        (function () {
            var lines = file.split("\n");
            for (var i = 34; i < lines.length; i++) {
                var attrs = lines[i].split(",");
                elements[i - 34] = {
                    url: attrs[0],
                    id: attrs[1],
                    tagName: attrs[2],
                    childsNumber: attrs[3],
                    textLength: attrs[4],
                    basePlatform: attrs[5],
                    targetPlatform: attrs[6],
                    baseBrowser: attrs[7],
                    targetBrowser: attrs[8],
                    baseDPI: attrs[9],
                    targetDPI: attrs[10],
                    baseScreenshot: attrs[11],
                    targetScreenshot: attrs[12],
                    baseX: attrs[13],
                    targetX: attrs[14],
                    baseY: attrs[15],
                    targetY: attrs[16],
                    baseHeight: attrs[17],
                    targetHeight: attrs[18],
                    baseWidth: attrs[19],
                    targetWidth: attrs[20],
                    baseParentX: attrs[21],
                    targetParentX: attrs[22],
                    baseParentY: attrs[23],
                    targetParentY: attrs[24],
                    imageDiff: attrs[25],
                    chiSquared: attrs[26],
                    baseDeviceWidth: attrs[27],
                    targetDeviceWidth: attrs[28],
                    baseViewportWidth: attrs[29],
                    targetViewportWidth: attrs[30],
                    xpath: attrs[31]
                };
            };
        }());

        paintPosition = function () {
            var positionBase = document.createElement("div"),
                positionTarget = document.createElement("div"),
                oldPositions = document.querySelectorAll(".position");

            for (var i = 0; i < oldPositions.length; i++) {
                oldPositions[i].parentElement.removeChild(oldPositions[i]);
            };

            positionBase.className = "position";
            positionBase.style.position = "absolute";
            positionBase.style.backgroundColor = "red";
            positionBase.style.opacity = "0.3";
            positionBase.style.top = (parseInt(elements[count].baseY)) + "px";
            positionBase.style.left = (parseInt(elements[count].baseX)) + "px";
            positionBase.style.height = elements[count].baseHeight + "px";
            positionBase.style.width = elements[count].baseWidth + "px";
            positionBase.style.margin = "0";
            screenshots.querySelectorAll(".screenshots > div")[0].appendChild(positionBase);

            positionTarget.className = "position";
            positionTarget.style.position = "absolute";
            positionTarget.style.backgroundColor = "red";
            positionTarget.style.opacity = "0.3";
            positionTarget.style.top = (parseInt(elements[count].targetY)) + "px";
            positionTarget.style.left = (parseInt(elements[count].targetX)) + "px";
            positionTarget.style.height = elements[count].targetHeight + "px";
            positionTarget.style.width = elements[count].targetWidth + "px";
            positionTarget.style.margin = "0";
            screenshots.querySelectorAll(".screenshots > div")[1].appendChild(positionTarget);
        };

        return {
            loadImage: function () {
                var imgBase = document.createElement("img"),
                    imgTarget = document.createElement("img"),
                    baseContainer = document.createElement("div"),
                    targetContainer = document.createElement("div");
                imgBase.src = filename.split('-')[0] + "/complete.png";
                imgTarget.src = filename.split('-')[1].split('.')[0] + "/complete.png";
                baseContainer.appendChild(imgBase);
                targetContainer.appendChild(imgTarget);
                screenshots.innerHTML = "";
                screenshots.appendChild(baseContainer);
                screenshots.appendChild(targetContainer);
            },
            getCurrent: function () {
                var imgBase = document.createElement("img"),
                    imgTarget = document.createElement("img");
                imgBase.src = elements[count].baseScreenshot.replace("results/", "");
                imgTarget.src = elements[count].targetScreenshot.replace("results/", "");
                container.innerHTML = "";
                container.appendChild(imgBase);
                container.appendChild(imgTarget);
                cursor.innerHTML = count + " - " + imgBase.src;

                if (row_tr)
                    table.removeChild(row_tr);
                row_tr = document.createElement("tr");
                row_tr.className = "row";
                row_tr.innerHTML = "<td>" + (elements[count].baseX) + "</td>" +
                                   "<td>" + (elements[count].targetX) + "</td>" +
                                   "<td>" + (elements[count].baseY) + "</td>" +
                                   "<td>" + (elements[count].targetY) + "</td>" +
                                   "<td>" + (elements[count].baseHeight) + "</td>" +
                                   "<td>" + (elements[count].targetHeight) + "</td>" +
                                   "<td>" + (elements[count].baseWidth) + "</td>" +
                                   "<td>" + (elements[count].targetWidth) + "</td>" +

                                   "<td>" + (parseInt(elements[count].baseX) - parseInt(elements[count].targetX)) + "</td>" +
                                   "<td>" + (parseInt(elements[count].baseY) - parseInt(elements[count].targetY)) + "</td>" +
                                   "<td>" + (parseInt(elements[count].baseHeight) - parseInt(elements[count].targetHeight)) + "</td>" +
                                   "<td>" + (parseInt(elements[count].baseWidth) - parseInt(elements[count].targetWidth)) + "</td>" +
                                   "<td>" + (elements[count].imageDiff) + "</td>" +
                                   "<td>" + (elements[count].basePlatform != 'null') + "</td>" +
                                   "<td>" + (elements[count].targetPlatform != 'null') + "</td>";
                table.appendChild(row_tr);

                paintPosition();

                return elements[count];
            },
            next: function () {
                count = Math.min(count + 1, elements.length - 1);
                this.getCurrent();
            },
            previous: function () {
                count = Math.max(count - 1, 0);
                this.getCurrent();
            },
            classify: function (result) {
                elements[count].Result = result;
                this.next();
            },
            report: function () {
                var r = "";
                for (var i = 0; i < elements.length; i++) {
                    var row = elements[i].url + "," +
                              elements[i].id + "," +
                              elements[i].tagName + "," +
                              elements[i].childsNumber + "," +
                              elements[i].textLength + "," +
                              elements[i].basePlatform + "," +
                              elements[i].targetPlatform + "," +
                              elements[i].baseBrowser + "," +
                              elements[i].targetBrowser + "," +
                              elements[i].baseDPI + "," +
                              elements[i].targetDPI + "," +
                              elements[i].baseScreenshot + "," +
                              elements[i].targetScreenshot + "," +
                              elements[i].baseX + "," +
                              elements[i].targetX + "," +
                              elements[i].baseY + "," +
                              elements[i].targetY + "," +
                              elements[i].baseHeight + "," +
                              elements[i].targetHeight + "," +
                              elements[i].baseWidth + "," +
                              elements[i].targetWidth + "," +
                              elements[i].baseParentX + "," +
                              elements[i].targetParentX + "," +
                              elements[i].baseParentY + "," +
                              elements[i].targetParentY + "," +
                              elements[i].imageDiff + "," +
                              elements[i].chiSquared + "," +
                              elements[i].baseDeviceWidth + "," +
                              elements[i].targetDeviceWidth + "," +
                              elements[i].baseViewportWidth + "," +
                              elements[i].targetViewportWidth + "," +
                              elements[i].xpath + "," +
                              elements[i].Result;
                    r += row + "\n"
                };
                return r;
            }
        };
    };

    var arquivo = document.querySelector("#arquivo"),
        classifier = document.querySelector("#classifier"),
        container = document.querySelector("#container"),
        cursor = document.querySelector("#cursor"),
        table = document.querySelector("table"),
        row_tr = document.querySelector("table .row"),
        screenshots = document.querySelector(".screenshots"),
        conclude = document.querySelector("button"),
        report = document.querySelector("#report");
    arquivo.addEventListener("change", function () {
        var self = this,
            reader = new FileReader();
        reader.onload = function (e) {
            var n = Navigator(reader.result, screenshots, container, cursor, self.files[0].name);
            n.loadImage();
            n.getCurrent();
            classifier.addEventListener("keyup", function (ev) {
                if (ev.keyCode === 39 || ev.keyCode === 40)
                    n.next();
                if (ev.keyCode === 37 || ev.keyCode === 38)
                    n.previous();
                if (ev.keyCode === 13) {
                    n.classify(this.value);
                    this.value = "";
                }
            });
            conclude.addEventListener("click", function () {
                var result = n.report();
                report.innerHTML = result;
            });
            classifier.focus();
        };
        reader.readAsText(self.files[0]);
    });
}());
