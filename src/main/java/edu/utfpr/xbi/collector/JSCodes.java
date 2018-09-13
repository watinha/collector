package edu.utfpr.xbi.collector;

public class JSCodes {
    public final static String QUERY_ALL_ELEMENTS =
        //"var all = document.querySelectorAll('body *');" +
        //"window.elements = [];" +
        //"for (var i = 0; i < all.length; i++) {" +
        //    "if (all[i].querySelectorAll('*').length < 20) {" +
        //        "window.elements.push(all[i]);" +
        //    "}" +
        //"}" +
        //"return window.elements.length;";
        //"var expected = document.querySelectorAll('body *')," +
        //    "all = []," +
        //    "targetList = []," +
        //    "target, children," +
        //    "max = 100, count = 1;" +
        //"targetList.push(document.body);" +
        //"while (targetList.length != 0 && count < max) {" +
        //    "target = targetList.shift();" +
        //    "children = target.children;" +
        //    "for (var i = 0; i < children.length; i++) {" +
        //        "all.push(children[i]);" +
        //        "targetList.push(children[i]);" +
        //        "count++;" +
        //    "};" +
        //"}" +
        "window.elements = document.querySelectorAll('body *'); return window.elements.length;";
    public final static String GET_ELEMENT =
        "return window.elements[%d];";
    public final static String GET_PARENT =
        "return window.elements[%d].parentElement;";
    public final static String HIDE_ELEMENT =
        "return window.elements[%d].style.opacity=0;";
    public final static String GET_XPATH =
        "var target = arguments[0];" +
        "var xpath = '', tagName, parent = target.parentElement," +
            "index, children;" +
        "while (parent != null) {" +
            "tagName = target.tagName.toLowerCase();" +
            "children = [].slice.call(parent.children);" +
            "index = children.indexOf(target) + 1;" +
            "xpath = '/' + tagName + '[' + index + ']' + xpath;" +
            "target = parent;" +
            "parent = target.parentElement;" +
        "};" +
        "return xpath;";

    public final static String COLLECTOR_JS =
        "var path = arguments[0]," +
        "    url = arguments[1]," +
        "    browser = arguments[2]," +
        "    deviceWidth = arguments[3]," +
        "    viewportWidth = arguments[4]," +
        "    dpi = arguments[5]," +
        "    index = arguments[6]," +
        "    max = window.elements.length;" +
        "function getXPath (target) {" +
        "   var xpath = '', tagName, parent = target.parentElement," +
        "       index, children;" +
        "   while (parent != null) {" +
        "       tagName = target.tagName.toLowerCase();" +
        "       children = [].slice.call(parent.children);" +
        "       index = children.indexOf(target) + 1;" +
        "       xpath = '/' + tagName + '[' + index + ']' + xpath;" +
        "       target = parent;" +
        "       parent = target.parentElement;" +
        "   };" +
        "   return xpath;" +
        "}" +
        "window.rows = [];" +
        "for (var i = (max - 1); i >= 0; i--){" +
        "   var target = window.elements[i]," +
        "       parent = target.parentElement," +
        "       tagName = (target.tagName ? target.tagName.toUpperCase() : '');" +
        "   if (tagName && (tagName !== 'SCRIPT' && tagName !== 'STYLE' && tagName !== 'LINK' && tagName !== 'NOSCRIPT' && parseInt($(target).outerWidth()) !== 0 && parseInt($(target).outerHeight()) !== 0) && $(target).is(':visible')) {" +
        "       var row = path + ',' + url + ',' + i + ',' + target.tagName + ',' + browser + ',' + parseInt($(target).offset().left) + ',' + parseInt($(target).offset().top) + ',' + parseInt($(target).outerHeight()) + ',' + parseInt($(target).outerWidth()) + ',' + parseInt($(parent).offset().left) + ',' + parseInt($(parent).offset().top) + ',' + deviceWidth + ',' + viewportWidth + ',' + dpi + ',' + (target.querySelectorAll('*').length) + ',' + target.innerHTML.length + ',' + getXPath(target);" +
        "       window.rows.push(row);" +
        "   }" +
        "}" +
        "return window.rows.join('\\n');";
}
