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
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
    <link href="https://cdn.datatables.net/1.10.19/css/dataTables.bootstrap.min.css" rel="stylesheet" type="text/css"/>
    <!--<link href="resources/css/dataTables.bootstrap.min.css" rel="stylesheet" type="text/css"/>-->
    <link href="resources/css/bootstrap.css" rel="stylesheet" type="text/css"/>
    <link href="resources/css/Style.css" rel="stylesheet" type="text/css"/>
    <link href="resources/css/buttons.css" rel="stylesheet" type="text/css"/>
    <link href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet" type="text/css"/>
    <link href="resources/Libraries/skyforms/css/sky-forms.min.css" rel="stylesheet" type="text/css"/>
    <link href="resources/Libraries/Hover-master/css/hover.css" rel="stylesheet" type="text/css"/>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/animate.css/3.5.2/animate.min.css">
    
    <script src="resources/js/jquery-2.2.3.js" type="text/javascript"></script>
    <script src="resources/js/easytimer.js" type="text/javascript"></script>
    <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
    <script src="https://cdn.datatables.net/1.10.19/js/jquery.dataTables.min.js"></script>
    <script src="https://cdn.datatables.net/1.10.19/js/dataTables.bootstrap.min.js"></script>
    <script src="resources/Libraries/skyforms/js/jquery.maskedinput.min.js"></script>
    <script src="resources/Libraries/skyforms/js/jquery.form.min.js"></script>
    <script src="resources/Libraries/skyforms/js/jquery.validate.min.js"></script>
    
    <script src="resources/js/bootstrap.js" type="text/javascript"></script>
    <script src='https://cdnjs.cloudflare.com/ajax/libs/xlsx/0.13.2/xlsx.core.min.js'></script>
    <script src='http://oss.sheetjs.com/js-xlsx/xlsx.full.min.js'></script>
</head>