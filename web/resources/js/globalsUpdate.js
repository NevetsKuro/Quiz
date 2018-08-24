$(document).ready(function () {
   
        var rABS = true; // true: readAsBinaryString ; false: readAsArrayBuffer
        function to_json(workbook) {
            var result = {};
            workbook.SheetNames.forEach(function(sheetName) {
                var roa = XLSX.utils.sheet_to_row_object_array(workbook.Sheets[sheetName]);
                if(roa.length > 0){
                    result[sheetName] = roa;
                }
            });
            return result;
        }
        var ress;
        function handleFile(e) {
          var files = e.target.files, f = files[0];
          if(!f){
              return false;
          }
          var reader = new FileReader();
          reader.onload = function(e) {
            var data = e.target.result;
            if(!rABS) data = new Uint8Array(data);
            var workbook = XLSX.read(data, {type: rABS ? 'binary' : 'array'});
                        ress = to_json(workbook);
//                        if(ress){
//                        validation(ress);
//                        }
                        console.log(ress.Sheet1);
          };
          if(rABS) reader.readAsBinaryString(f); else reader.readAsArrayBuffer(f);
        }

        $(document).on('click','#SendExcel',function(e) {
            e.preventDefault();
            var av = $('#export')[0].files[0];
            if(ress && av){
                $.ajax({
                    url:'questionExcelList',//URL
                    type: 'POST',
                    dataType: 'JSON',
                    data:{json:JSON.stringify(ress.Sheet1)},
                    success: function (data) {
                        swal("Success");
                    },
                    error: function (error) {
                        swal("Success");
                        console.log(error.responseText);
                    }
                });
            }else{
                swal('Upload a file!');
            }
        });
        
        $(document).on('change','#export',handleFile);
                        
        function validation(res) {
            var valid = true;
            var ext = $('#export')[0].files[0].name.split('.')[1];
            if(ext!='xlsx'){
                swal("please add a excel format file only!!");
                $('#SendExcel').attr('disabled',true);
                valid = false;
            }
            else if(ext=='xlsx'){
                for (var i = 0; i < res.Sheet1.length; i++) {
                    if(res.Sheet1[i].hasOwnProperty('SPARE_ID')&&
                    res.Sheet1[i].hasOwnProperty('MAKE')&&
                    res.Sheet1[i].hasOwnProperty('MODEL')&&
                    res.Sheet1[i].hasOwnProperty('ITEM')&&
                    res.Sheet1[i].hasOwnProperty('SERIAL_NO')&&
                    res.Sheet1[i].hasOwnProperty('NO_OF_PORTS')&&
                    res.Sheet1[i].hasOwnProperty('PRINTER_TYPE')&&
                    res.Sheet1[i].hasOwnProperty('RAM')&&
                    res.Sheet1[i].hasOwnProperty('HDD')&&
                    res.Sheet1[i].hasOwnProperty('ITEM_PARENTS')&&
                    res.Sheet1[i].hasOwnProperty('LOCATION')&&
                    res.Sheet1[i].hasOwnProperty('VALUE')&&
                    res.Sheet1[i].hasOwnProperty('DATE_IN')){
                        //if all fields exist
                        console.log("all clear!!");
                    }else{
                        $('#SendExcel').attr('disabled',true);
                        swal('Please do not leave any blank fields at row '+ i+' or use NA in blank fields');
                        valid = false;
                        break;
                    }
                    if(res.Sheet1[i].DATE_IN){
                        if(res.Sheet1[i].DATE_IN.split('-').length != 3&&!res.Sheet1[i].DATE_IN.split('-')[0]>12&&!res.Sheet1[i].DATE_IN.split('-')[2].length==4){
                            $('#SendExcel').attr('disabled',true);
                            valid = false;
                            swal('Your date should match this format /n (MM-DD-YYYY) i.e. 12-25-2018');
                        }
                    }
                }
            }
            else{
                $('#export').val("");
                valid = false;
                swal('Please add a xlsx file');
            }
            if(valid){
                $('#SendExcel').attr('disabled',false);
            }
        }
        
        var qtnDatatable = $('#questionDatable').DataTable({
            "order": [[1, 'asc']],
            "pageLength": 10,
            "ordering": false,
            "info":     false,
            "searching":false
        });
        
        $(document).on('click','#refresh',function (){
            $(this).addClass('fa-spin');
            $.ajax({
                url:'questionDetails',
                type: 'POST',
                dataType:'JSON',
                success: function (data) {
                    var tabData = data;
                    var counter = 1;
                    qtnDatatable.clear();
                    $.each(tabData, function( key, value ) {
                        qtnDatatable.row.add([
                            counter,
                            value.QUES_DESC,
                            value.OPTION1,
                            value.OPTION2,
                            value.OPTION3,
                            value.OPTION4,
                            value.CORRECT_OP
                        ]).draw();
                        counter++;
                    });
                    qtnDatatable.columns.adjust().draw();
                    console.log('Done' + JSON.stringify(data));
                    $('#refresh').removeClass('fa-spin');
                    swal('Data Refreshed');
                },
                error:function (error){
                    $('#refresh').removeClass('fa-spin');
                    console.log(error);
                }
           });
        });
});


