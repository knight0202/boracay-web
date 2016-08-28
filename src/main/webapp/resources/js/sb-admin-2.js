$(function() {
    window.require = function(script) {
        $.ajax({
            url: script,
            dataType: "script",
            async: false,           // <-- This is the key
            success: function () {
                console.info("%s is loaded.",script)
            },
            error: function () {
                throw new Error("Could not load script " + script);
            }
        });
    }

    window.requireHtml = function(script) {
        $.ajax({
            url: script,
            dataType: "html",
            async: false,           // <-- This is the key
            success: function (result) {
                console.info("%s is loaded.",script);
                $('body').append($(result))
            },
            error: function () {
                throw new Error("Could not load html " + script);
            }
        });
    };    

    $.sjh = (function($, window){
        var _self = this;
        var  errorCb = function(a,b,c){

        };
        
        _self.ajax = function(opt){
            if(!opt.url )
                throw new Error("Mendatory is missing.")

            var data = $.param(opt.data || {});

            $.ajax({
                dataType: opt.dataType || 'json',
                async: opt.async || true,
                complete: opt.complete || null,
                data: data,
                type: opt.type || 'GET',
                url: opt.url,
            }).done(function(data, xhr){
                if(opt.done)
                    opt.done.call(opt.context ,data, xhr);
            }).fail(function(xhr,status, error){
                // alert("에러가 발생하였습니다.");
                // console.error(xhr);
                // console.error(error);
            });
        }

        return this;
    }).call({},jQuery, window);
});






//Loads the correct sidebar on window load,
//collapses the sidebar on window resize.
// Sets the min-height of #page-wrapper to window size
$(function() {   

    new WOW().init();

    $(window).bind("load resize", function() {
        topOffset = 50;
        width = (this.window.innerWidth > 0) ? this.window.innerWidth : this.screen.width;
        if (width < 768) {
            $('div.navbar-collapse').addClass('collapse')
            topOffset = 100; // 2-row-menu
        } else {
            $('div.navbar-collapse').removeClass('collapse')
        }

        height = (this.window.innerHeight > 0) ? this.window.innerHeight : this.screen.height;
        height = height - topOffset;
        if (height < 1) height = 1;
        if (height > topOffset) {
            $("#page-wrapper").css("min-height", (height) + "px");
        }
    })

    $(document).ready(function(){
        console.info(1);
    });
})
