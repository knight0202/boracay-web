if (!this.CHART) {
    this.CHART = {};
}

(function () {
	if (typeof CHART.render !== 'function') {    	
		CHART.render = function (settings) {
			//console.log(settings);
			
			try  {
				$.ajax({
					type : 'POST',
					url: settings.action + '.do?action=' + settings.action,
					dataType : 'json',
					async:false,
					data : {
						appName : settings.appName,
						period: settings.period,
						dateFromTime: settings.dateFromTime,
						dateToTime: settings.dateToTime,
						useragentlist: settings.useragentlist,
						//reqeustUrllist: settings.reqeustUrllist,
						region: settings.region
					},
					success : function (data) {
						
						var xaxis = [];
						var seriesArr = [];
						
						var legend = {
							enabled : false,
							layout : 'vertical',
							align : 'center',
							verticalAlign : 'bottom',
							borderWidth : 0
						};
						
						if(data.response.length > 1) {
							legend.enabled = true;
						} else {
							legend.enabled = false;
						}
						
						for(var i = 0; i < data.response.length;i++) {							
							var series = {name : "", data : []};
							series.name = "";
							for(var j = 0; j < data.response[i].length; j++) {
								if(i == 0) {
									xaxis.push(data.response[i][j].date);
								}
								//series.name = data.response[i][j].requestUrl;
								series.data.push(parseInt(data.response[i][j].count));	
							}
							seriesArr.push(series);
						}
						
						var options = {
							renderTo : settings.target[0].id,
							chart : {type : 'line'},
							title : {text : (settings.appName == '' ? "App. 전체" : settings.appName)  + " " + settings.period + " Chart"},
							xAxis : {categories : xaxis},
							yAxis : {
								title : {
									text : 'Count'
								},
								plotLines: [{
				                    value: 0,
				                    width: 1,
				                    color: '#808080'
				                }],
				                min: 0
							},
							tooltip : {
								shared : true,
								crosshairs : true
							},
							legend : legend,
							series : seriesArr,
							exporting : {
								enabled : false
							}
						};
						
						for(var i = 0; i < settings.target.length; i++) {
							options.chart.renderTo = settings.target[i].id;
							new Highcharts.Chart(options);
						}
						
						remeveTag();
					}, error : function () {
					}
				});			
			} catch (err) {
				console.log(err.message);
			}
		};
		
		function remeveTag() {
			$('text[text-anchor="end"]').each(function( index ) {
				 if($( this ).text() == "Highcharts.com") {
					 $( this ).remove();
				 }
			});
		}
	}
	
}());