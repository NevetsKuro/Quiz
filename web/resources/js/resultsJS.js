$(document).ready(function (){
   var ResultDatatable = $('#resultsListDatable').DataTable({
        "order": [[1, 'asc']],
        "pageLength": 10,
        "ordering": false
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
                    counter,
                    value.emp_code,
                    value.resultMarks,
                    value.timeTaken,
                    value.hasSubmitted,
                    '<span data-code="'+value.emp_code+'" class="retest button button-primary button-pill button-small" style:"font-family:sans-serif">Re-test</span>'
                ]).draw();
                counter++;
            });
            console.log('Done');
            
        },
        error:function (error){
            console.log("Error");
        }
   });
   
   var Result2Datatable = $('#downloadDatable').DataTable({
        dom: 'Bfrtip',
        buttons: [
            'excel','copy'
        ]
    });
    $(document).on('click','#downloadExcel',function () {

        $.ajax({
            url:'ResultController?url=downloadExcel',
            type:'POST',
            dataType: 'JSON',
            success:function(data) {
                console.log(data);
                var counter = 1;
                Result2Datatable.clear();
                $.each(data, function( key, value ) {
                    Result2Datatable.row.add([
                        counter,
                        value.emp_name,
                        value.emp_code,
                        value.loc_code,
                        value.marks,
                        value.duration
                    ]).draw();
                    counter++;
                });
                $('#downloadDatable_wrapper > div.dt-buttons > button.dt-button.buttons-excel.buttons-html5').trigger('click');
            },
            error:function (error) {
                console.log(error);
            }
        });
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