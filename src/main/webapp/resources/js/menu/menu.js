$(function(){
	console.info("loaded..");
	var context = location.pathname.split("/")[1];

	$(window).bind("load",function(){		
		requireHtml("/" + context + "/resources/template/menu.html");

		$.sjh.ajax({
			url: '/'+context+'/info/facility',
			done: function(jData, xhr){				
				var tmplHtml = $('#tmpl-selFacility').html(),
					tmpl;	

				tmpl = _.template(tmplHtml, {list: jData});
				$('#sel-facility').html(tmpl);
			}
		});
	});

	$('#sel-facility').on('change', function(e){
		$.sjh.ajax({
			url: '/'+context+'/info/irrigation',
			data: {
				id: $(this).val()
			},
			done: function(jData, xhr){
				var o = jData.irrigationVo;
				for(var prop in jData.irrigationVo){
					$('#sel-ig-' + prop).val(o[prop]);
				}
				$('#sel-ig-non').val(o.rpa + o.dpa + o.fia);
				$('#sel-ig-total').val(o.rpa + o.dpa);
			}
		});

		$.sjh.ajax({
			url: '/'+context+'/info/weatherStation',
			data: {
				
			},
			done: function(jData, xhr){
				var tmplHtml = $('#tmpl-selWeatherStation').html(),
					tmpl;	

				tmpl = _.template(tmplHtml, {list: jData});
				$('#sel-weatherStation').html(tmpl);
			}
		});

		$.sjh.ajax({
			url: '/'+context+'/info/water/years',
			data: {
				id: $(this).val()
			},
			done: function(jData, xhr){
				var tmplHtml = $('#tmpl-selWaterYears').html(),
					tmpl;	

				tmpl = _.template(tmplHtml, {list: jData.stringList});
				$('#sel-require-water').html(tmpl);
				$('#sel-supply-water').html(tmpl);
			}
		});
	});

	$('#btn-search-requireWater').on('click', (function(){
		var $year = $('#sel-require-water'),
			$facility = $('#sel-facility');

		return function(e){
			var waterInfo, weatherInfo;

			$.sjh.ajax({
				url: '/'+context+'/info/water/dataList',
				data: {
					id: $facility.val(),
					year: $year.val(),
				},
				done: function(jData, xhr){				
					waterInfo = jData.waterVoList;
					weatherInfo = jData.weatherVoList;
				}
			});

		};
	})());
});