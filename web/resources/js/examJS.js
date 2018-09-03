
$(document).ready(function () {

    var quiz_time = parseInt($("#tqt").attr("data-time"));
    var quesArr=[],quesObj,ques,quesAns;
    var timer = new Timer();
    let totalTime = 0;
    totalTime = localStorage.getItem('timeTaken');
//    if(totalTime && totalTime!="0"){
//        quiz_time = parseInt(totalTime); 
//    }
    
    timer.start({
        countdown: true,
        startValues: {seconds: quiz_time}
    });
    timer.addEventListener('secondsUpdated', function (e) {
        $('#clockIt').html(timer.getTimeValues().toString());
//        var currentSeconds = parseInt(timer.getTotalTimeValues().seconds);
//        if (localStorage) {
//            // LocalStorage is supported!
//            localStorage.setItem('timeTaken', currentSeconds);
//        } else {
//            // No support. Use a fallback such as browser cookies or store on the server.
//            alert('Please use another browser');
//        }
    });
    timer.addEventListener('started', function (e) {
        $('#clockIt').html(timer.getTimeValues().toString());
    });
    timer.addEventListener('reset', function (e) {
        $('#clockIt').html(timer.getTimeValues().toString());
    });

    timer.addEventListener('targetAchieved', function (e) {
        $('#clockIt').html('TimeOut');
        setTimeout(submitted(), 2000);
    });

    function submitted(){
        $.ajax({
                async: true,
                url: 'saveorfinish?action=Submit',
                type: 'POST',
                data: {json:JSON.stringify(quesArr)},
                success: function(data) {
                    window.open('ResultController?url=Finish','_self');
                    localStorage.removeItem('timeTaken',null);
                },
                error:function(error){
                    console.log(error.responseText);
                }
            });
    }
    //////////////////////////////////////              Ajax Code               ////////////////////////////////////////////////////
    
    $.ajax({
        async: true,
        url: 'RetrieveQuesList',
        type: 'GET',
        dataType: 'JSON',
        success: function (data) {
            quesArr = data;
            console.log(data);
            if(data){
                jQuery.each(data, function (i, val) {
                $('#QuesTable > tbody > tr').each(function () {
                    if ($(this).find('td > span').attr('data-name') == val.quesNo) {
                            $(this).find('td > form > div.row').each(function () {
                            // console.log(val.quesNo + "?" + val.quesAns + " =" + ($(this).find('label > input').val() == val.quesAns));

                                if ($(this).find('label > input').val() == val.quesAns)
                                {
                                    $(this).find('label > input[type=radio]').prop('checked', true);
                                    console.log('done ' + val.quesNo);

                                }
                            });
                        }
                    });
                });                
            }
            populate(data);
            var examDatatable = $('#QuesTable').DataTable({
                "pageLength":1,
                "lengthChange": false,
                "ordering": false,
                "searching": false,
                "fixedColumns": {
                    heightMatch: 'none'
                }
            });
            $('#loader').addClass('hide');
            $('#mainBody').removeClass('hide');
            
            $(document).on('click','#summary > div',function(){
                var b = parseInt($(this).find('.cubes').html()) - 1;
                examDatatable.page(b).draw(false);
            });
        },
        error: function (error) {
            console.log(error.responseText);
        }
    });

    $(document).on('change','#QuesTable > tbody > tr:nth-child(1) > td > form > div',function(){
        if($(this).find('input[type="radio"]').is(':checked')){
            ques = $(this).find('input[type="radio"]').attr('name');
            quesAns = $(this).find('input[type="radio"]').attr('value');

            var found = quesArr.some(function (el) {
                return el.quesNo === ques;
            });
            if(!found){
                quesObj = new Object();
                quesObj.quesNo = ques;
                quesObj.quesAns = quesAns;
                quesArr.push(quesObj);
                console.log(quesObj.quesNo+' added');
            }else if(found){
                quesArr.find((a)=> a.quesNo==ques).quesAns = quesAns;
            }
            populate(quesArr); //fills the summary field with up to date selected data
        }
    });
 
    $(document).on('click','#clearRadio',function(){
        var name;
        $('#QuesTable > tbody > tr:nth-child(1) > td > form > div').each(function() {
            $(this).find('label > input').attr('checked',false);
            quesNo = $(this).find('label > input').attr('name');
        });
        var found = quesArr.some(function (el) {
            return el.quesNo === quesNo;
        });
        if(found){
            quesArr.find((a)=> a.quesNo==quesNo).quesAns = "0";
        }
        populate(quesArr); //fills the summary field with up to date selected data
    });
 
    function compare(a,b) {
        if (parseInt(a.quesNo) < parseInt(b.quesNo))
          return -1;
        if (parseInt(a.quesNo) > parseInt(b.quesNo))
          return 1;
        return 0;
    }
    
    function populate(data){
        $('#summary').html("");
        data.sort(compare);
        jQuery.each(data, function (i, val) {
            if(val.quesAns == 0){
                //<div style="width: 10%;height: 8%;padding:3px;background-color: white;margin: 9px;border-radius: 9px;font-size: large;box-shadow: 0 0 9px black;text-align: center; float:left;"><span class="cubes" data-queNo="${val.quesNo}">${i+1}</span></div>
                $('#summary').append(`
                <div style="width: 10%;height: 8%;background-color: white;margin: 9px;font-size: large; float:left;line-height: 1.7;box-shadow: 0 0 8px black;" class="button button-box"><span class="cubes" data-queNo="${val.quesNo}">${i+1}</span></div>
                `);
                //<div style="width: 10%;height: 8%;padding:3px;background-color: limegreen;margin: 9px;border-radius: 9px;font-size: large;box-shadow: 0 0 9px black;text-align: center; float:left;"><span class="cubes" id="${val.quesNo}">${i+1}</span></div>
            }else if(val.quesAns > 0){
                $('#summary').append(`
                    <div style="width: 10%;height: 8%;background-color: limegreen;margin: 9px;font-size: large;float:left;line-height: 1.7;box-shadow: 0 0 8px black;" class="button button-box"><span class="cubes" id="${val.quesNo}">${i+1}</span></div>
                `); 
            }
        });
    }
    
    $(document).on('click','#saveIt',function (e){
        e.preventDefault();
        $.ajax({
            async: true,
            url:'saveorfinish?action=Save',
            type:'POST',
            dataType:'JSON',
            data:{json:JSON.stringify(quesArr)},
            success:function(data){
                swal('Your answers are saved!!')
            },
            error:function(error){
                console.log(error.responseText);
            }
        });
    });
    
    $(document).on('click','#submitIt', function(e) {
        e.preventDefault();
        console.log('Submitting...');
        //table.rows().nodes().page.len(-1).draw(false); // This is needed
        swal({
            title: "Are you sure you want to Submit?",
            icon: "warning",
            buttons: true,
            dangerMode: true
          })
          .then((willDelete) => {
            if (willDelete) {
                timer.stop();
                submitted();
            } 
          });
    });
    
    $(document).on('click', '#nextIt', function () {
        
    });
});