<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" xmlns:f="http://java.sun.com/jsf/core" xmlns:h="http://java.sun.com/jsf/html" version="2.0">
    <jsp:directive.page language="java"
        contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
    <jsp:text>
        <![CDATA[ <!DOCTYPE html> ]]>
    </jsp:text>

<html lang="de">
  <head>
    <meta charset="utf-8" />
    <title>KIT Authenticator Tester</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <meta name="description" content="" />
    <meta name="author" content="" />

    <!-- Le styles -->
    <link href="/AuthTester/css/bootstrap.css" rel="stylesheet" />
    <link href="/AuthTester/css/main.css" rel="stylesheet" />
    <style>
      body {
        padding-top: 60px; /* 60px to make the container go all the way to the bottom of the topbar */
      }
    </style>
    <link href="css/bootstrap-responsive.css" rel="stylesheet" />

    <!-- Le HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
      <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->
   


  </head>

  <body>
    <f:view>

    <div class="navbar navbar-fixed-top">
      <div class="navbar-inner">
        <div class="container">
          <a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </a>
          <a class="brand" href="/AuthTester">KIT Authenticator Tester</a>
          <div class="nav-collapse">
            <ul class="nav">
              <li class="active"><a href="/AuthTester/monitor">Arbeitsplatz-Monitor</a></li>
              <li><a href="/AuthTester/devices">Mobilgeräte verwalten</a></li>
              <li><a href="/AuthTester/info.html">Info</a></li>
            </ul>
          </div><!--/.nav-collapse -->
        </div>
      </div>
    </div>

    <div id="workstations" class="container">

	    <div class="page-header">
	        <h1>Platzbelegung <small>Freie, besetzte und reservierte Arbeitsplätze</small></h1>
	    </div>

        <p id="qrcode" class="thumbnail"><img src="/AuthTester/img/qrcodes/${qrCodeFile.name}" /></p>

	    <div class="row">
		    <div class="span2"><a class="btn btn-danger">A1</a></div>
		    <div class="span2"><a class="btn btn-danger">A2</a></div>
		    <div class="span2"><a class="btn btn-danger">A3</a></div>
		    <div class="span2"><a class="btn btn-danger">A4</a></div>
		    <div class="span2"><a class="btn btn-danger">A5</a></div>
		    <div class="span2"><a class="btn btn-warning">A6</a></div>
	    </div>

        <div class="row">
            <div class="span2"><a class="btn btn-danger">B1</a></div>
            <div class="span2"><a class="btn btn-danger">B2</a></div>
            <div class="span2"><a class="b3 btn btn-warning">B3 <span class="bubble"><strong>André Simmert</strong><em>Reserviert ab 14:30 Uhr</em></span></a></div>
            <div class="span2"><a class="btn btn-danger">B4</a></div>
            <div class="span2"><a class="btn btn-warning">B5</a></div>
            <div class="span2"><a class="btn btn-danger">B6</a></div>
        </div>

        <div class="row">
            <div class="span2"><a class="btn btn-warning">C1</a></div>
            <div class="span2"><a class="btn btn-warning">C2</a></div>
            <div class="span2"><a class="btn btn-danger">C3</a></div>
            <div class="span2"><a class="btn btn-success">C4</a></div>
            <div class="span2"><a class="btn btn-success">C5</a></div>
            <div class="span2"><a class="btn btn-success">C6</a></div>
        </div>

        <div class="row">
            <div class="span2"><a class="btn btn-danger">D1</a></div>
            <div class="span2"><a class="btn btn-success">D2</a></div>
            <div class="span2"><a class="btn btn-warning">D3</a></div>
            <div class="span2"><a class="btn btn-success">D4</a></div>
            <div class="span2"><a class="btn btn-danger">D5</a></div>
            <div class="span2"><a class="btn btn-success">D6</a></div>
        </div>

        <div class="row">
            <div class="span2"><a class="btn btn-danger">E1</a></div>
            <div class="span2"><a class="btn btn-danger">E2</a></div>
            <div class="span2"><a class="btn btn-warning">E3</a></div>
            <div class="span2"><a class="btn btn-success">E4</a></div>
            <div class="span2"><a class="btn btn-danger">E5</a></div>
            <div class="span2"><a class="btn btn-danger">E6</a></div>
        </div>

    </div>

  </f:view>

    <!-- Le javascript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <jsp:text>
	    <script src="/AuthTester/js/jquery.js">/* */</script>
	    <script src="/AuthTester/js/bootstrap.js">/* */</script>
	    <script src="/AuthTester/js/main.js">/* */</script>
    </jsp:text>

  </body>
</html>

</jsp:root>
