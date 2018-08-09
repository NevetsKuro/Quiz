<html>
    <%@include file="../UItemplate/header2.jsp" %>
    <body>
        <div class="row">
            <div class="col-lg-offset-1 col-lg-8" style="padding: 40px;">
                <table id="mainBody" class="table table-striped table-hover table-bordered">
                        <thead>
                            <th>Sr.No</th>
                            <th>Parameters</th>
                            <th>Value
                                <span class="buttonload" style="position: absolute;right:55px;position: absolute;right:45px;top:44px;border: 1px solid dodgerblue;border-radius: 49%;background-color: aliceblue;color: dodgerblue;padding: 7px;touch-action: manipulation;cursor: pointer">
                                    <i class="fa fa-refresh" id="refresh" style="font-weight: bolder; margin:0px;" title="click to refresh the data"></i>
                                </span>
                            </th>
                        </thead>
                        <tbody>
                            <tr>
                                <td>1</td>
                                <td data-name="Site_Link">Site Link</td>
                                <td id="slink">-</td>
                            </tr>
                            <tr>
                                <td>2</td>
                                <td data-name="correct_ans_mark">Correct answer(Marks)</td>
                                <td id="cAns">-</td>
                            </tr>
                            <tr>
                                <td>3</td>
                                <td data-name="wrong_ans_mark">Wrong Answer(Marks)</td>
                                <td id="wAns">-</td>
                            </tr>
                            <tr>
                                <td>4</td>
                                <td data-name="s_datetime">Start Date</td>
                                <td id="sDate">-</td>
                            </tr>
                            <tr>
                                <td>5</td>
                                <td data-name="e_datetime">End Date</td>
                                <td id="eDate">-</td>
                            </tr>
                            <tr>
                                <td>6</td>
                                <td data-name="result_flag">Result Flag</td>
                                <td id="resFlag">-</td>
                            </tr>
                            <tr>
                                <td>7</td>
                                <td data-name="quiz_no_questions">No of Questions(Quiz)</td>
                                <td id="noQues">-</td>
                            </tr>
                            <tr>
                                <td>8</td>
                                <td data-name="quiz_time">Total Quiz Time</td>
                                <td id="quizTime">-</td>
                            </tr>
                            <tr>
                                <td>9</td>
                                <td data-name="quiz_name">Quiz Name</td>
                                <td id="quizName">-</td>
                            </tr>
                            <tr>
                                <td>10</td>
                                <td data-name="total_no_questions">Total No of questions</td>
                                <td id="totsNoQues">-</td>
                            </tr>
                            <tr>
                                <td>11</td>
                                <td data-name="random_flag">Random Flag</td>
                                <td id="randFlag">-</td>
                            </tr>
                        </tbody>
                        
                    </table>
                </div>
        </div>
        
        
        
        <div id="valueModal" class="modal fade" role="dialog">
        <div class="modal-dialog">

          <!-- Modal content-->
          <div class="modal-content">
            <div class="modal-header">
              <button type="button" class="close" data-dismiss="modal">&times;</button>
              <h4 class="modal-title">Change Value</h4>
            </div>
            <div class="modal-body">
                <form>
                    <div class="form-group">
                        <label for="newValue">Current Value for <span id="param"></span> is:<span id="currentValue"> - - </span></label>
                    <input id="newValue" type="text" class="form-control" placeholder="Enter a new value">
                </form>
            </div>
            <div class="modal-footer">
                <button id="submitGlobal" type="button" class="btn btn-success" data-dismiss="modal">Submit</button>
            </div>
          </div>
              <script src="resources/js/mastersJS.js" type="text/javascript"></script>
        </div>
      </div>
    </body>
</html>
