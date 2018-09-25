$(document).ready(function (){
    
    
    $(document).on('change','input[type=checkbox]',function (){
        $('#frating').val($(this).val());
    });
    
    var flag = 0;
    $(document).on('click', '#subFeedback', function (e) {
        e.preventDefault();
        var name = $('#fname').val();
        var ratings = $('#frating').val();
        var comment = $('#fcomment').val();
        
        if(flag==0)
        {   
        flag=1;
        $.ajax({
            url: 'FeedbackController?fname='+name+'&fratings='+ratings+'&fcomment='+comment,
            type: 'POST',
            dataType: 'JSON',
            success: function (data) {
                swal('Feedback submitted');
                console.log(data);
                flag=0;
            },
            error: function (error) {
                swal('Unsuccesful. Try again later.');
                console.log(error);
                flag=0;
            }
        });
    }
    });
    
});