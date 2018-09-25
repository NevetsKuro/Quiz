$(document).ready(function(){
    
    var a = parseInt($('#timeIs').attr('data-seconds'));
    var timer = new Timer();
    var b = Math.floor(a/3600);
    if(b>23){
        timer.start({countdown: true, startValues: {hours: b}});
    }else if(b<=23){
        timer.start({countdown: true, startValues: {seconds: a}});
    }
    console.log("Time is "+b);
//    $('timeIs').html(timer.getTimeValues().toString());
    timer.addEventListener('secondsUpdated', function (e) {
        var c = Math.floor(a/3600);
        if(b>23){
            $('#timeIs').html(timer.getTotalTimeValues().hours+' hours');
        }else if(b<=23){
            $('#timeIs').html(timer.getTimeValues().toString());
        }
        $('#timeR').removeClass('hide');
    });
    timer.addEventListener('targetAchieved', function (e) {
        location.reload();
    });
    
    $(document).on('click', '#linkTo', function () {
        window.open('jsps/showExcel.jsp','_blank');
    });
});

