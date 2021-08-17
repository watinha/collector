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

        "function offset (el) { " +
        "    const rect = el.getBoundingClientRect(), " +
        "          win = el.ownerDocument.defaultView; " +
        "    return { " +
        "        top: rect.top + win.pageYOffset, " +
        "        left: rect.left + win.pageXOffset " +
        "    }; " +
        "} " +
        "function dimension (el) { " +
        "    return { " +
        "        height: (el.offsetHeight ? el.offsetHeight : 0), " +
        "        width: (el.offsetWidth ? el.offsetWidth : 0) " +
        "    }; " +
        "} " +

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
        "       aux," +
        "       parent = target.parentElement," +
        "       previous_sibling, next_sibling," +
        "       tagName = (target.tagName ? target.tagName.toUpperCase() : '')," +
        "       text_nodes = 0," +
        "       font_family = window.getComputedStyle(target).fontFamily;" +
        "" +
        "   aux = target;" +
        "   while (aux.parentElement != null && aux.previousElementSibling == null)" +
        "      aux = aux.parentElement;" +
        "   previous_sibling = aux.previousElementSibling;" +
        "   aux = target;" +
        "   while (aux.parentElement != null && aux.nextElementSibling == null)" +
        "      aux = aux.parentElement;" +
        "   next_sibling = aux.nextElementSibling;" +
        "" +
        "   target.childNodes.forEach(function(node) { if(node.nodeType === 3) text_nodes++; });" +
        "" +
        "   if (tagName && tagName !== 'HTML' && " +
                           "tagName !== 'SCRIPT' && " +
                           "tagName !== 'STYLE' && " +
                           "tagName !== 'LINK' && " +
                           "tagName !== 'NOSCRIPT' && " +
                           "parseInt(dimension(target).width) !== 0 && " +
                           "parseInt(dimension(target).height) !== 0 && " +
                           "parseInt(dimension(target).width + offset(target).left) > 0 && " +
                           "parseInt(offset(target).left) < viewportWidth) { " +
                           //"target.isVisible()) {" +
        "       var row = path + ',' + url + ',' + i + ',' + target.tagName + ',' + browser + ',' +" +
        "           parseInt(offset(target).left) + ',' + " +
        "           parseInt(offset(target).top) + ',' + " +
        "           parseInt(dimension(target).height) + ',' + " +
        "           parseInt(dimension(target).width) + ',' + " +
        "           parseInt(offset(parent).left) + ',' + " +
        "           parseInt(offset(parent).top) + ',' + " +
        "           deviceWidth + ',' + viewportWidth + ',' + dpi + ',' + " +
        "           (target.querySelectorAll('*').length) + ',' + " +
        "           target.innerHTML.length + ',' + getXPath(target) + ',' + " +
        "           (previous_sibling ? parseInt(offset(previous_sibling).left) : 0) + ',' + " +
        "           (previous_sibling ? parseInt(offset(previous_sibling).top) : 0) + ',' + " +
        "           (next_sibling ? parseInt(offset(next_sibling).left) : 0) + ',' + " +
        "           (next_sibling ? parseInt(offset(next_sibling).top) : 0) + ',' + " +
        "           text_nodes + ',' + " +
        "           font_family;" +
        "       window.rows.push(row);" +
        "   }" +
        "}" +
        "return window.rows.join('\\n');";
}
