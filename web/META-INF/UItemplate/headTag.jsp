<%-- 
    Document   : headTag
    Created on : 16 Dec, 2016, 10:29:34 AM
    Author     : Manish Jangir
--%>
<head>
    <%
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Expires", "0");
        response.setDateHeader("Expires", -1);
    %>

    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>${quiz_name}</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="resources/css/bootstrap.css" rel="stylesheet" type="text/css"/>
    <link href="resources/css/Style.css" rel="stylesheet" type="text/css"/>
    <script src="resources/js/jquery-2.2.3.js" type="text/javascript"></script>
    <script src="resources/js/jquery.dataTables.min.js" type="text/javascript"></script>
    <script src="resources/js/dataTables.bootstrap.min.js" type="text/javascript"></script>
    <script src="resources/js/siteScript.js" type="text/javascript"></script>
    <script src="resources/js/easytimer.js" type="text/javascript"></script>
    <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
    <script type="text/javascript">
        window.onload = function () {
            var start_time = new Date("${start_time}");
            var now = new Date();
            var timeDiff = start_time.getTime() - now.getTime();
            if (timeDiff > 0) {
                document.getElementsByName("submit")[0].style.visibility = "hidden";
            }
        };
            function full_screen()
            {
                console.log("fullscreen func running!!")
                // check if user allows full screen of elements. This can be enabled or disabled in browser config. By default its enabled.
                //its also used to check if browser supports full screen api.
                if("fullscreenEnabled" in document || "webkitFullscreenEnabled" in document || "mozFullScreenEnabled" in document || "msFullscreenEnabled" in document) 
                {
                    if(document.fullscreenEnabled || document.webkitFullscreenEnabled || document.mozFullScreenEnabled || document.msFullscreenEnabled)
                    {
                        console.log("User allows fullscreen");
                    
                        var element = document.getElementById("box");
                        //requestFullscreen is used to display an element in full screen mode.
                        if("requestFullscreen" in element) 
                        {
                            element.requestFullscreen();
                        } 
                        else if ("webkitRequestFullscreen" in element) 
                        {
                            element.webkitRequestFullscreen();
                        } 
                        else if ("mozRequestFullScreen" in element) 
                        {
                            element.mozRequestFullScreen();
                        } 
                        else if ("msRequestFullscreen" in element) 
                        {
                            element.msRequestFullscreen();
                        }

                    }
                }
                else
                {
                    console.log("User doesn't allow full screen");
                }
            }

            function screen_change()
            {
                //fullscreenElement is assigned to html element if any element is in full screen mode.
                if(document.fullscreenElement || document.webkitFullscreenElement || document.mozFullScreenElement || document.msFullscreenElement) 
                {
                    console.log("Current full screen element is : " + (document.fullscreenElement || document.webkitFullscreenElement || document.mozFullScreenElement || document.msFullscreenElement))
                }
                else
                {
                    // exitFullscreen us used to exit full screen manually
                    if ("exitFullscreen" in document) 
                    {
                        document.exitFullscreen();
                    } 
                    else if ("webkitExitFullscreen" in document) 
                    {
                        document.webkitExitFullscreen();
                    } 
                    else if ("mozCancelFullScreen" in document) 
                    {
                        document.mozCancelFullScreen();
                    } 
                    else if ("msExitFullscreen" in document) 
                    {
                        document.msExitFullscreen();
                    }
                }
            }
        function testTimer() {
            var test = new Date("${start_time}");
            var now = new Date();
            var timeDiff = test.getTime() - now.getTime();
            if (timeDiff <= 0) {
                document.getElementsByName("submit")[0].style.visibility = "visible";
                document.getElementById("clockDisplay").style.display = "none";
                document.getElementById("clockcontainer").style.display = "none";
            }
            var secs = Math.floor(timeDiff / 1000);
            var mins = Math.floor(secs / 60);
            var hours = Math.floor(mins / 60);
            hours %= 24;
            mins %= 60;
            secs %= 60;
            if (secs < 10) {
                secs = "0" + secs;
            }
            if (mins < 10) {
                mins = "0" + mins;
            }
            if (hours < 10) {
                hours = "0" + hours;
            }
            document.getElementById("clockDisplay").innerHTML = hours + ":" + mins + ":" + secs;
            var timer = setTimeout('testTimer()', 1000);
        }
    </script>
</head>