<html>
    <%@include file="../UItemplate/header2.jsp" %>
    <body>
        <div id="main_body">
            <form class="row">
                
                <div class="row" style="padding-top: 37px">
                    <div class="col-lg-offset-1 col-lg-3"> 
                        <label>Upload your Question Files Here:</label>
                        <div>
                        <input id="export" type="file" class="btn btn-default">
                        </div><br>
                    </div>
                    <div class="col-lg-1" style="margin-top:25px;">
                    <button id="SendExcel" class="btn btn-success">Upload</button>
                    </div>
                </div>
            </form>
            <div class="row">
                <div class="col-lg-offset-1 col-lg-10">
                    <!--<span style="position: absolute;right:55px;position: absolute;right:34px;font-size: 22px;border: 1px solid dodgerblue;border-radius: 49%;background-color: aliceblue;color: dodgerblue;padding: 7px;">
                        <i id="refresh" class="glyphicon glyphicon-refresh" style="font-weight: bolder;" title="click to refresh the data"></i>
                    </span> -->
                    <span class="buttonload" style="position: absolute;right:34px;font-size: 22px;border: 1px solid dodgerblue;border-radius: 49%;background-color: aliceblue;color: dodgerblue;padding: 7px;touch-action: manipulation;cursor: pointer">
                        <i class="fa fa-refresh" id="refresh" style="font-weight: bolder; margin:0px;" title="click to refresh the data"></i>
                    </span>
                    <table id="questionDatable" class="table table-responsive table-bordered">
                        <thead>
                            <tr>
                                <th>Question ID</th>
                                <th>Questions</th>
                                <th>OPTION1</th>
                                <th>OPTION2</th>
                                <th>OPTION3</th>
                                <th>OPTION4</th>
                                <th>Correct Option</th>
                            </tr>
                        </thead>
                        <tbody>

                        </tbody>
                    </table>
                </div>
            </div>
        </div>
        <script src="resources/js/globalsUpdate.js" type="text/javascript"></script>
        
    </body>
</html>
