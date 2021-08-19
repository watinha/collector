/**
 * Author: Jason Farrell
 *         Willian Massami Watanabe (just included jQuery usage)
 * Author URI: http://useallfive.com/
 *
 * Description: Checks if a DOM element is truly visible.
 * Package URL: https://github.com/UseAllFive/true-visibility
 */
Element.prototype.isVisible = function() {

    'use strict';
    function offset (el) {
        const rect = el.getBoundingClientRect(),
              win = el.ownerDocument.defaultView;
        return {
            top: rect.top + win.pageYOffset,
            left: rect.left + win.pageXOffset
        };
    }
    function dimension (el) {
        return {
            height: (el.offsetHeight ? el.offsetHeight : 0),
            width: (el.offsetWidth ? el.offsetWidth : 0)
        };
    }

    /**
     * Checks if a DOM element is visible. Takes into
     * consideration its parents and overflow.
     *
     * @param (el)      the DOM element to check if is visible
     *
     * These params are optional that are sent in recursively,
     * you typically won't use these:
     *
     * @param (t)       Top corner position number
     * @param (r)       Right corner position number
     * @param (b)       Bottom corner position number
     * @param (l)       Left corner position number
     * @param (w)       Element width number
     * @param (h)       Element height number
     */
    function _isVisible(el, t, r, b, l, w, h) {
        var p = el.parentNode,
                VISIBLE_PADDING = 2,
                VISIBLE_CLASSNAME = 'visibility_true',
                result;

        if (el.classList.contains(VISIBLE_CLASSNAME))
          return true;

        if ( !_elementInDocument(el) ) {
            return false;
        }

        //-- Return true for document node
        if ( 9 === p.nodeType ) {
            return true;
        }

        //-- Return false if our element is invisible
        if (
             '0' === _getStyle(el, 'opacity') ||
             'none' === _getStyle(el, 'display') ||
             'hidden' === _getStyle(el, 'visibility')
        ) {
            return false;
        }

        if (
            'undefined' === typeof(t) ||
            'undefined' === typeof(r) ||
            'undefined' === typeof(b) ||
            'undefined' === typeof(l) ||
            'undefined' === typeof(w) ||
            'undefined' === typeof(h)
        ) {
            t = offset(el).top;
            l = offset(el).left;
            h = dimension(el).height;
            w = dimension(el).width;
            b = t + h;
            r = l + w;
        }
        //-- If we have a parent, let's continue:
        if ( p && p.tagName && p.tagName !== 'BODY' ) {
            //-- Check if the parent can hide its children.
            if ( ('hidden' === _getStyle(p, 'overflow') || 'scroll' === _getStyle(p, 'overflow')) ) {
                //-- Only check if the offset is different for the parent
                if (
                    //-- If the target element is to the right of the parent elm
                    l + VISIBLE_PADDING > dimension(p).width + offset(p).left ||
                    //-- If the target element is to the left of the parent elm
                    l + w - VISIBLE_PADDING < offset(p).left ||
                    //-- If the target element is under the parent elm
                    t + VISIBLE_PADDING > dimension(p).height + offset(p).top ||
                    //-- If the target element is above the parent elm
                    t + h - VISIBLE_PADDING < offset(p).top
                ) {
                    //-- Our target element is out of bounds:
                    return false;
                }
            }
            //-- Let's recursively check upwards:
            result = _isVisible(p, t, r, b, l, w, h);
            if (result)
              p.classList.add(VISIBLE_CLASSNAME);
            return result;
        }
        return true;
    }

    //-- Cross browser method to get style properties:
    function _getStyle(el, property) {
        if ( window.getComputedStyle ) {
            return document.defaultView.getComputedStyle(el,null)[property];
        }
        if ( el.currentStyle ) {
            return el.currentStyle[property];
        }
    }

    function _elementInDocument(element) {
        while (element = element.parentNode) {
            if (element == document) {
                    return true;
            }
        }
        return false;
    }

    return _isVisible(this);

};
return "";
