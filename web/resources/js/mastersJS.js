$(document).ready(function () {
        
        $(document).on('click','#mainBody > tbody > tr',function(){
            console.log('id: '+$(this).find('td:nth-child(3)').attr('id'));
            $('#currentValue').html($(this).find('td:nth-child(3)').html());  //store current value
            $('#param').html($(this).find('td:nth-child(2)').attr('data-name')); //store param
            $('#newValue').val($(this).find('td:nth-child(3)').html());
            $('#valueModal').modal('show');
            $('#newValue').focus();
        });
        
        $(document).on('click','#submitGlobal',function (){
            var name = $('#param').html(); //param
            var newValue = $('#newValue').val(); //get new value
            
            $.ajax({
                url:'adminController?name='+name+'&newValue='+newValue,
//                url:'adminController',
                type:'POST',
//                data:{name:name,newValue:newValue},
                contentType:"application/json; charset=utf-8",
                success: function (data) {
                    $('#refresh').trigger('click');
                },
                error:function(error){
                    $('#refresh').trigger('click');
                }
            });
        });
        
        $(document).on('click','#refresh',function () {
            $('#refresh').addClass('fa-spin');
            $.ajax({
                url:'adminController',
                type:'GET',
                dataType:'JSON',
                success:function(data){
                    var globalData = data;
                    $('#slink').html(globalData.Site_Link);
                    $('#cAns').html(globalData.correct_ans_mark);
                    $('#wAns').html(globalData.wrong_ans_mark);
                    $('#sDate').html(globalData.s_datetime);
                    $('#eDate').html(globalData.e_datetime);
                    $('#resFlag').html(globalData.result_flag);
                    $('#noQues').html(globalData.quiz_no_questions);
                    $('#quizTime').html(globalData.quiz_time);
                    $('#quizName').html(globalData.quiz_name);
                    $('#totsNoQues').html(globalData.total_no_questions);
                    $('#randFlag').html(globalData.random_flag);
                    $('#refresh').removeClass('fa-spin');
                    swal('Data refreshed');
                },
                error:function(error){
                    $('#refresh').removeClass('fa-spin');
                    console.log(error.responseText);
                }
            });
        });
        
        $(document).on('change', '#newValue', function () {
            var value = $('currentValue').val();
            var str = $(this).val();
            var par = $("#param").html();
            switch (par) {
                case "Site_Link ":
                    if(str.indexOf("http://10.146.65.10/")<0){
                        swal("Please prepend the following String :-http://10.146.65.10/");
                        $(this).val("http://10.146.65.10/");
                        $('#submitGlobal').attr('disabled',true);
                    }
                    break;    
                case "correct_ans_mark":
                    if(parseInt(str) < 0){
                        swal("Correct Marks cannot be less than Zero");
                        $(this).val("");
                        $('#submitGlobal').attr('disabled',true);
                    }
                    break;
                case "wrong_ans_mark":
                    if(parseInt($('#cAns').val()) > parseInt(str)){
                        swal("Correct Marks cannot be less than Zero");
                        $(this).val("");
                        $('#submitGlobal').attr('disabled',true);
                    }
                    break;
                case "result_flag":
                    if(str == "ON"||str == "OFF"){
                        //do nothing
                    }else{
                        swal("Incorrect Value.(Should be ON or OFF)");
                        $(this).val("");
                        $('#submitGlobal').attr('disabled',true);
                    }
                    break;
                case "random_flag":
                    if(str == "ON"||str == "OFF"){
                        //do nothing
                    }else{
                        swal("Incorrect Value.(Should be ON or OFF)");
                        $(this).val("");
                        $('#submitGlobal').attr('disabled',true);
                    }
                    break;
                case "quiz_time":
                    if(parseInt(str) <= 0){
                        swal("Time cannot be less than or equal to zero!");
                        $(this).val("");
                        $('#submitGlobal').attr('disabled',true);
                    }
                    break;
                case "quiz_no_questions":
                    if(parseInt(str) >= parseInt($('#totsNoQues').html())){
                        swal("Quiz's number of question should be less than Total question mentioned!");
                        $(this).val("");
                        $('#submitGlobal').attr('disabled',true);
                    }
                    break;
            }
            if($(this).val() != ""){
                $('#submitGlobal').attr('disabled',false);
            }
        });
        
        $(document).on('click','#mainBody > tbody > tr:nth-child(7)',function (e) {
            e.preventDefault();
            
        });
        
        
        
        $('#refresh').trigger('click');
});