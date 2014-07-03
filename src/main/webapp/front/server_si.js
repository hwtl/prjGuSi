
//Module dependencies
var express = require('express')
  , http = require('http')
  , path = require('path');

var app = express();

// All environments
app.set('port', process.env.PORT || 3000);

app.set('views', __dirname + '/main');	//入口页路径映射

app.engine('html', require('ejs').renderFile);
app.set('view engine', 'ejs');
app.use(express.favicon());
app.use(express.logger('dev'));
app.use(express.bodyParser());
app.use(express.methodOverride());
app.use(app.router);

app.use('/static/front', express.static(__dirname)); //前端资源根目录(front)映射到static下

// Development only
if ('development' == app.get('env')) {
  app.use(express.errorHandler());
}

app.get('/si/common/index/*', function(req, res){//后端路由
	res.render('index.html');
});


require('./si/js/mock')(app); //mock data


//监听
http.createServer(app).listen(app.get('port'), function(){
  console.log('Express server listening on port ' + app.get('port'));
});