$(document).ready(function () {
        
        $(document).on('click','#mainBody > tbody > tr',function(){
            console.log('id: '+$(this).find('td:nth-child(3)').attr('id'));
            $('#currentValue').html($(this).find('td:nth-child(3)').html());  //store current value
            $('#param').html($(this).find('td:nth-child(2)').attr('data-name')); //store param
            $('#newValue').val($(this).find('td:nth-child(3)').html());
            $('#valueModal').modal('show');
        });
        
        $(document).on('click','#submitGlobal',function (){
            var name = $('#param').html(); //param
            var newValue = $('#newValue').val(); //get new value
            
            $.ajax({
                url:'adminController?name='+name+'&newValue='+newValue,
                type:'POST',
                dataType:'JSON',
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
        
        function validation2(name) {
            switch (name) {
                    case 'Site Link':

                        break;
                    case 'Correct answer(Marks)':

                        break;
                    case 'Wrong Answer(Marks)':

                        break;
                    case 'Start Date':

                        break;
                    case 'End Date':

                        break;
                    case 'Result Flag':

                        break;
                    case 'No of Questions(Quiz)	':

                        break;
                    case 'Total Quiz Time':

                        break;
                    case 'Quiz Name':

                        break;
                    case 'Total No of questions':

                        break;
                    case 'Random Flag':

                        break;
                    default:

                        break;
                }
                return true;
        }
        $('#refresh').trigger('click');
});