$(document).ready(function (){
   var ResultDatatable = $('#resultsListDatable').DataTable({
        "order": [[1, 'asc']],
        "pageLength": 10,
        "ordering": false,
        "searching": false
   });
   
   $.ajax({
        url:'empResultDetails',
        type: 'POST',
        dataType:'JSON',
        success: function (data) {
            var tabData = data;
            var counter = 1;
            $.each(tabData, function( key, value ) {
                ResultDatatable.row.add([
                    counter +1,
                    value.emp_code,
                    value.resultMarks,
                    value.timeTaken,
                    value.hasSubmitted,
                    '<span data-code="'+value.emp_code+'" class="retest button button-primary button-pill button-small">Re-test</span>'
                ]).draw();
                counter++;
            });
            console.log('Done' + data);
            
        },
        error:function (error){
            console.log("Error");
        }
   });
   
   $(document).on('click','.retest',function (){
        var code = $(this).attr('data-code');
        var action = "retest";
        $.ajax({
            url:'employeeController?action='+action+'&id='+code,
            type: 'POST',
            dataType: 'JSON',
            success:function (data){
                swal('Test cleared for employee '+ code);
                location.reload();
            },
            error: function (error) {
                console.log(error);
            }
        });
    });
});