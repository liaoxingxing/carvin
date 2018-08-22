/*
开发者：王亮
功能:移动端组件封装
时间:2014.5.5
*/

/*默认值*/
Object.extend = function (destination, source) {
    if (!destination) return source;
    for (var property in source) {
        if (!destination[property]) {
            destination[property] = source[property];
        }
    }
    return destination;
}

var eventNames = ['webkit', 'moz', 'o'];
(function ($) {
    //transition事件监听
    $.fn.transitionEnd = function (options) {
        var setting = {
            listen: 'TransitionEnd',
            end: null
        }
        options = Object.extend(options, setting);
        var $this = this;
        function seatTransitionEnd() {
            for (var i = 0; i < eventNames.length; i++) {
                if (eventNames[i] == 'moz') {
                    $this.removeEvent(options.listen.toLocaleLowerCase(), seatTransitionEnd);
                } else {
                    $this.removeEvent(eventNames[i] + options.listen, seatTransitionEnd);
                }
            }
            options.end && options.end.call($this);
        }
        for (var i = 0; i < eventNames.length; i++) {
            if (eventNames[i] == 'moz') {
                $this.addEvent(options.listen.toLocaleLowerCase(), seatTransitionEnd);
            } else {
                $this.addEvent(eventNames[i] + options.listen, seatTransitionEnd);
            }
        }
    }

    //添加事件
    $.fn.addEvent = function (name, fn) {
        var obj = this[0];
        var cName = name.charAt(0).toUpperCase() + name.substring(1);
        for (var i = 0; i < eventNames.length; i++) {
            obj.addEventListener(eventNames[i] + cName, fn, false);
        }
        obj.addEventListener(name.charAt(0).toLowerCase() + name.substring(1), fn, false);
    }

    //删除事件
    $.fn.removeEvent = function (name, fn) {
        var obj = this[0];
        var cName = name.charAt(0).toUpperCase() + name.substring(1);
        for (var i = 0; i < eventNames.length; i++) {
            obj.removeEventListener(eventNames[i] + cName, fn, false);
        }
        obj.removeEventListener(name.charAt(0).toLowerCase() + name.substring(1), fn, false);
    }

    //触摸屏事件
    $.fn.touches = function (options) {
        var setting = {
            init: null,//初始化
            touchstart: null,  //按下
            touchmove: null, //滑动
            touchend: null //抬起
        };
        options = Object.extend(options, setting);
        var $this = this, touchesDiv = $this[0];
        if (!$this[0]) return;
        touchesDiv.addEventListener('touchstart', function (ev) {
            options.touchstart && options.touchstart.call($this, ev);

            function fnMove(ev) {

                options.touchmove && options.touchmove.call($this, ev);
            }

            function fnEnd(ev) {
                options.touchend && options.touchend.call($this, ev);
                document.removeEventListener('touchmove', fnMove, false);
                document.removeEventListener('touchend', fnEnd, false);
            }
            document.addEventListener('touchmove', fnMove, false);
            document.addEventListener('touchend', fnEnd, false);
            return false;
        }, false)
        options.init && options.init.call($this);
    }

    //右侧弹出层
    $.fn.rightSwipeAction = function (options) {
        var setting = {
            show: 'swipeLeft-block',
            clickEnd: null
        };
        options = Object.extend(options, setting);
        var $child = $(this.children(1)), display = 'none';
        if ($child.hasClass(options.show)) {
            display = 'none';
        } else {
            display = 'block';
        }
        options.clickEnd && options.clickEnd.call($child, display);
    };

    //右侧附加选择层插件
    $.fn.rightSwipe = function (options) {
        var $temp = null;
        var setting = {
            isclick: null,
            zindex: 999999,
            back: '.leftmask',
            alert: '.leftPopup',
            clickEnd: null, //打开关闭层回调事件
            oneEnd: null,
            closeEnd: null,
            currentid: null,
            selected: false, ///如果为真，查找currentid是否相等，如果符合就触发回调事件
            onBeforeScrollStart: function (ev) {
                ev.preventDefault();
            },
            clickCallBack: function (clickEnd) { //点击默认回调方法
                clickEnd.call(this);
            }
        };

        function clickEnd() {
            var $this = this;
            var $leftPopup = $('.leftPopup.' + $this.attr('data-action'));
            options.zindex = parseInt($leftPopup.attr('data-zindex')) == 0 ? options.zindex : parseInt($leftPopup.attr('data-zindex'))
            $leftPopup[0].style.zIndex = options.zindex;
            options.oneEnd && options.oneEnd.call($leftPopup);
            var $back = $('.' + $leftPopup.attr('data-back'));
            if ($back.length > 0) {
                $back.css('z-index', options.zindex - 10000);
                $back.show();
            }
           
            $leftPopup.rightSwipeAction({
                clickEnd: function (display) {
                    if (display != 'none') {
                        var $swipeLeft = $leftPopup.find('.swipeLeft');
                        $leftPopup.show();
                        setTimeout(function () { $swipeLeft.addClass('swipeLeft-block'); }, 200);
                        if ($back.length > 0) {
                            $back.parents('body').css('overflow', 'hidden');
                            $back.on('close', function (ev, params) {
                                if (!params || !params.leftPopup) {
                                    setTimeout(function () {
                                        $(options.back).each(function (index, curr) {
                                            curr.style.display = 'none';
                                        })
                                    }, 300);
                                    var $alert = $(options.alert).children().removeClass('swipeLeft-block').end();
                                    $back.parents('body').css('overflow', 'inherit');
                                    setTimeout(function () { $alert.hide(); options.closeEnd }, 200);
                                    options.closeEnd && options.closeEnd.call($swipeLeft, $back);
                                } else {
                                    var $alert = params.leftPopup.children().removeClass('swipeLeft-block').end();
                                    setTimeout(function () { $alert.hide(); params.leftPopup[0].style.display = 'none'; }, 200);

                                    options.closeEnd && options.closeEnd.call(params.leftPopup.children(), $back);
                                }

                            })

                            $back.touches({
                                touchstart: function () {
                                    $back.trigger('close');
                                }
                            });
                        }
                        $swipeLeft.transitionEnd({ end: function () { options.clickEnd && options.clickEnd.call($leftPopup, true, $this); } })
                    } else {
                        options.clickEnd && options.clickEnd.call($leftPopup, true, $this);
                    }
                }
            });
        }
        options = Object.extend(options, setting);
        if (this.length == 0) { return; }
        this.each(function (index, curr) {
            var $curr = $(curr);
            (function ($this) {
                $this.isclick = true;
                $this.click(function (ev) {
                    options.onBeforeScrollStart.call($this, ev);
                    options.isclick && ($this.isclick = options.isclick.call($this));
                    if ($this.isclick == false) {
                        return;
                    }
                    options.clickCallBack.call($this, clickEnd);
                })
                //查找默认选中值，如果符合就触发回调事件
                if (options.selected && options.currentid && options.currentid.toString() == $curr.attr('data-id')) {
                    options.clickCallBack.call($this, clickEnd)
                    return;
                }
            })($curr);
        })
    }
})(jQuery);

window.addEventListener('touchmove', function () { });