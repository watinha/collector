(function () {
    var Navigator = function (file, screenshots, container, cursor) {
        var file = file,
            elements = [],
            count = 0,
            paintPosition;

        (function () {
            var lines = file.split("\n");
            for (var i = 43; i < lines.length; i++) {
                var attrs = lines[i].split(",");
                elements[i - 43] = {
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
                    xpath: attrs[31],
                    baseXpath: attrs[32],
                    targetXpath: attrs[33],
                    phash: attrs[34],
					basePreviousSiblingLeft: attrs[35],
					targetPreviousSiblingLeft: attrs[36],
					basePreviousSiblingTop: attrs[37],
					targetPreviousSiblingTop: attrs[38],
					baseNextSiblingLeft: attrs[39],
					targetNextSiblingLeft: attrs[40],
					baseNextSiblingTop: attrs[41],
					targetNextSiblingTop: attrs[42],
					baseTextNodes: attrs[43],
					targetTextNodes: attrs[44],
					baseFontFamily: attrs[45],
					targetFontFamily: attrs[46]
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
            positionBase.style.opacity = "0.9";
            positionBase.style.top = (parseInt(elements[count].baseY)) + "px";
            positionBase.style.left = (parseInt(elements[count].baseX)) + "px";
            positionBase.style.height = elements[count].baseHeight + "px";
            positionBase.style.width = elements[count].baseWidth + "px";
            positionBase.style.margin = "0";
            screenshots.querySelectorAll(".screenshots > div")[0].appendChild(positionBase);

            positionTarget.className = "position";
            positionTarget.style.position = "absolute";
            positionTarget.style.backgroundColor = "red";
            positionTarget.style.opacity = "0.9";
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
                    targetContainer = document.createElement("div"),
                    i = 0, j = 0;
                while(elements[i].baseScreenshot == 'null') {i++; console.log(i); console.log(elements[i]); }
                while(elements[j].targetScreenshot == 'null') j++;
                imgBase.src = elements[i].baseScreenshot.split("/")[1] + "/complete.png";
                imgTarget.src = elements[j].targetScreenshot.split("/")[1] + "/complete.png";
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
                cursor.innerHTML = count;

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
                              elements[i].baseXpath + "," +
                              elements[i].targetXpath + "," +
                              elements[i].phash + "," +
							  elements[i].basePreviousSiblingLeft + "," +
							  elements[i].targetPreviousSiblingLeft + "," +
							  elements[i].basePreviousSiblingTop + "," +
							  elements[i].targetPreviousSiblingTop + "," +
							  elements[i].baseNextSiblingLeft + "," +
							  elements[i].targetNextSiblingLeft + "," +
							  elements[i].baseNextSiblingTop + "," +
							  elements[i].targetNextSiblingTop + "," +
							  elements[i].baseTextNodes + "," +
							  elements[i].targetTextNodes + "," +
							  elements[i].baseFontFamily + "," +
							  elements[i].targetFontFamily + "," +
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
        screenshots = document.querySelector(".screenshots"),
        conclude = document.querySelector("button"),
        report = document.querySelector("#report");
    arquivo.addEventListener("change", function () {
        var self = this,
            reader = new FileReader();
        reader.onload = function (e) {
            var n = Navigator(reader.result, screenshots, container, cursor);
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
