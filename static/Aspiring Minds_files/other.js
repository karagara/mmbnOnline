//http://jsbin.com/ucolu
function scrollDivShow()
{
    $('#scrollDiv').css('display', "block");
}

$(window).load(function()
{
/*      if( $("#tdNextStartTest").length )
        {
                $("#tdNextStartTest")[0].setAttribute("onclick",'scrollDivShow()');
                $("#tdNextStartTest").find("span:first").css("background","linear-gradient(grey,grey)");
        }
*/
    $('.text-scroll').css({'height': (($(window).height()) - 280) + 'px'});
    $(window).bind('resize', function() {
        $('.text-scroll').css({'height': (($(window).height()) - 280) + 'px'});
					});

    $('.scroll-inner').css({'height': (($(window).height()) - 220) + 'px'});
    $(window).bind('resize', function() {
        $('.scroll-inner').css({'height': (($(window).height()) - 220) + 'px'});
        	});
//    $('.tabContents').css({'height': (($(window).height()) - 275) + 'px'});
//    $(window).bind('resize', function() {
//        $('.tabContents').css({'height': (($(window).height()) - 275) + 'px'});
//        });

			
    function adjustContainerHeight(selector){
        var wHeight = $(window).height();
        $(selector).each(function(){
            if($(this).parent().attr('id') === 'disclaimerContainer'){
                $(this).css({'height': (wHeight - 140) + 'px'}); // Fix for Terms & Conditions
                $(this).find('#disclaimerContent').css({'height': (wHeight - 211) + 'px'}); // Fix for Terms & Conditions
            } else {
                $(this).css({'height': (($(window).height()) - 220) + 'px'});
            }
        });
    }
    adjustContainerHeight('.page-sec-inner');
				
    $(window).bind('resize', function() {
        adjustContainerHeight('.page-sec-inner');
        });
				

    $('.page-sec-inner-resume').css({'height': (($(window).height()) - 135) + 'px'});
    $(window).bind('resize', function() {
        $('.page-sec-inner-resume').css({'height': (($(window).height()) - 135) + 'px'});
        });
				
    $('.page-sec-inner3').css({'height': (($(window).height()) - 185) + 'px'});
    $(window).bind('resize', function() {
        $('.page-sec-inner3').css({'height': (($(window).height()) - 185) + 'px'});
        });
				
    $('.page-sec-inner2').css({'height': (($(window).height()) - 300) + 'px'});
    $(window).bind('resize', function() {
        $('.page-sec-inner2').css({'height': (($(window).height()) - 300) + 'px'});
        });
				
				
    $('.page-sec-inner4').css({'height': (($(window).height()) - 160) + 'px'});
    $(window).bind('resize', function() {
        $('.page-sec-inner4').css({'height': (($(window).height()) - 160) + 'px'});
            //alert('resized');
        });
				
				
				
    $('.page-sec-inner5').css({'height': (($(window).height()) - 40) + 'px'});
    $(window).bind('resize', function() {
        $('.page-sec-inner5').css({'height': (($(window).height()) - 40) + 'px'});
            //alert('resized');
        });
				
				
				  //FAQ effect
			
						$('.set-of-tasks h3').each(function() {
        var tis = $(this), state = false, answer = tis.next('.hide').hide().css('height', 'auto').slideUp();
							tis.click(function() {
								state = !state;
								answer.slideToggle(state);
            tis.toggleClass('active', state);
                                                        });
                                                });
/*
    if( $("#tdNextStartTest").length && $(".page-sec-inner").get(0).scrollHeight > $(".page-sec-inner").get(0).clientHeight )
    {
        $("#tdNextStartTest")[0].setAttribute("onclick",'scrollDivShow()');
        $("#tdNextStartTest").find("span:first").css("background","linear-gradient(grey,grey)");
    }

    $(".page-sec-inner").scroll(function() 
	{
        var c1 = $(".page-sec-inner").scrollTop();
        var c2 = $(".page-sec-inner").innerHeight();
        var c3 = $(".page-sec-inner")[0].scrollHeight;
        if (c1 + c2 == c3)
        {//$("#tdNextStartTest").removeAttr('disabled');
            $("#tdNextStartTest").find("span:first").css("background", "linear-gradient(#4d8bc8, #4d8ac6)");
            $("#tdNextStartTest")[0].setAttribute("onclick", 'return doSubmitTest("NextQuestion")');
        }
    });
*/                                                
});
