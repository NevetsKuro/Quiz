$(document).ready(function(){
    //code for get the list of employees details
    
    var EmpListDatatable = $('#EmpListDatable').DataTable({
       "order": [[1, 'asc']],
        "pageLength": 10,
        "ordering": true,
        "searching": true 
    });
    
    
    var action = 'retrieve';
    $.ajax({
            url:'employeeController?action='+action,
            type: 'POST',
            dataType: 'JSON',
            success:function (data){
                console.log(data);
                var counter = 0;
                $.each(data, function( key, value ) {
                    console.log(value.emp_name);
//                    if(counter<80){
                        EmpListDatatable.row.add([
                            counter+1,
                            value.emp_code,
                            value.emp_name,
                            value.loc_code,
                            value.emp_design,
                            value.company_code,
                            '<span data-code="'+value.emp_code+'" class="button button-primary button-pill button-small changeRole">'+value.role+'</span>'//buttons for updating and 
                        ]).draw(false);
                        counter++;
//                    }
                });
                
                $('#loader').addClass('hide');
                $('.empTable').removeClass('hide');
            },
            error: function (error) {
                console.log(error);
            }
        });
        
    $(document).on('click','.changeRole',function (){
        var code = $(this).attr('data-code');
        var role = $(this).html();
        var action = "roleChange";
        $.ajax({
            url:'employeeController?action='+action+'&id='+code+'&role='+role,
            type: 'POST',
            dataType: 'JSON',
            success:function (data){
                swal('Employee '+data+' role has been changed');
                setTimeout(location.reload());
            },
            error: function (data) {
                swal('Unsuccessfull!');
            }
        });
    });
});