$(document).ready(function() {

    function hideAllBut(doNotHide){
        $(".contentB_food").hide();
        $(".contentB_news").hide();
        $(".contentB_sn").hide();
        $(".contentB_events").hide();
        $(doNotHide).show();
    }


    function controllerB(){
        /*
         hideAllBut(".contentB_news");
         news(function(){
         //hideAllBut(".contentB_sn");
         //fbPosts(function(){
         hideAllBut(".contentB_events");
         events(function(){
         hideAllBut(".contentB_food");
         menus();
         });
         //});
         });
         */
        hideAllBut(".contentB_news");
        news().done(function(){
            console.log('CHISDE');
            hideAllBut(".contentB_events");
            events().done(function(){
                hideAllBut(".contentB_food");
                menus().done(function(){
                    hideAllBut(".contentB_sn");
                    fbPosts();
                });
            });
        });
    }

    controllerB();

    /*
     *  Menus
     */
    function menus(){
        var r = $.Deferred();

        var menus = [];

        $.getJSON( 'menus.json' , function(res){
            $.each(res , function(){
                menus.push(this);
            });
            listMenus(0);
        });

        function listMenus(i){
            var timerId = setInterval( function(){
                var entry = menus[i];
                $('.contentB_food').html(
                    '<div>' +
                        '<h2>' + entry.restaurantName + '</h2>' +
                        '<ul>' +
                        '<li>' + entry.description + '</li>' +
                        '</ul>' +
                        '</div>'
                );

                if(i + 1 == menus.length){
                    clearInterval(timerId);
                    r.resolve();
                }
                else
                    listMenus(i+1);

            }, 1000);
        }
        return r;
    }

    /**
     *  Noticias
     */
    function news(){
        var r = $.Deferred();

        var news = [];

        $.getJSON( 'news.json' , function(res){
            $.each(res , function(){
                news.push(this);
            });

            listNews(0);
        });

        function listNews(i){
            var timerId = setInterval( function(){
                var entry = news[i];
                $('.contentB_news').html(
                    '<div>'+
                        '<h2>' + entry.title + '</h2>'+
                        '<p>' + entry.description + '</p>'+
                        '</div>'
                );

                if(i + 1 == news.length){
                    clearInterval(timerId);
                    r.resolve();
                }
                else
                    listNews(i+1);

            }, 1000);
        }
        return r;
    }

    /*
     *  Eventos
     */
    function events(){
        var r = $.Deferred();

        var events = [];

        $.getJSON( 'events.json' , function(res){
            $.each(res , function(){
                events.push(this);
            });
            listEvents(0);
        });

        function listEvents(i){
            var timerId = setInterval( function(){
                var entry = events[i];
                console.log(entry);
                $('.contentB_events').html(
                    '<div>' +
                        '<h2>' + entry.title + '</h2>' +
                        '<img src="http://upload.wikimedia.org/wikipedia/pt/f/f0/500px-SL_Benfica_logo_svg.png"/>' +
                        '<br><span>' + entry.longitude + ' - ' + entry.latitude + '</span>' +
                        '<h3>' + entry.eventDate + '</h3>' +
                        '</div>'
                );

                if(i + 1 == events.length){
                    clearInterval(timerId);
                    r.resolve();
                }
                else
                    listEvents(i+1);

            }, 1000);
        }
        return r;
    }

    /*
     *  Facebook statuses - B
     */
    function fbPosts(callback){
        $.getJSON( 'https://graph.facebook.com/v2.0/194463487272538/posts?limit=4&access_token=606956812659052|9c2cfa2141c57ab26908fdcb8d3c4679' , function(res){
            $.each(res.data , function(){
                var msg = "";
                if(this.hasOwnProperty('message'))
                    msg = this.message;
                else if(this.hasOwnProperty('caption'))
                    msg = this.caption;
                else if(this.hasOwnProperty('description'))
                    msg = this.description;
                else if(this.hasOwnProperty('name'))
                    msg = this.name;
                else
                    msg = null;
                if(msg != null){
                    $('<li></li>').append(
                        '<img class="snlogo" src="./fblogo.png"/><p>' + msg + '</p>'
                    ).appendTo('.contentB_sn ul');
                }
            });
            callback();
        });
    }
    /*
     *  RSS feed - C
     */
    var rssLinks = [
        'http://feeds.dn.pt/DN-Portugal',
        'http://feeds.dn.pt/DN-Globo',
        'http://feeds.dn.pt/DN-Economia',
        'http://feeds.dn.pt/DN-Ciencia',
        'http://feeds.dn.pt/DN-Artes',
        'http://feeds.dn.pt/DN-Opiniao',
        'http://feeds.dn.pt/DN-Desporto',
        'http://feeds.dn.pt/DN-Cartaz',
        'http://feeds.dn.pt/DN-Politica'
    ];

    function updateFeedContentC (entry){
        $('.contentC').fadeToggle("slow", function(){
            $('.contentC h1').html(entry.title);
            $('.contentC p').html(entry.content);
            $('.contentC').fadeToggle();
        });
    }

    function rss(callback){
        for(var j = 0; j < rssLinks.length; j++)
            callback(j);
    }

    function getFeeds(j){
        $.ajax({
            url:'http://ajax.googleapis.com/ajax/services/feed/load?v=1.0&q=' + rssLinks[j] + '&num=-1',
            success: function(data){
                var i = 0;
                $('#desc').html(rssLinks[j].substr(rssLinks[j].indexOf("-")+1,rssLinks[j].length));
                var timerId = setInterval(function(){
                    var entry = data.responseData.feed.entries[i];
                    updateFeedContentC(entry);
                    i++;
                    if( i == data.responseData.feed.entries.length){
                        clearInterval(timerId);
                        getFeeds((j + 1) % rssLinks.length);
                    }
                }, 2000);
            },
            error: function(error){
                console.log(this.url);
                console.log(JSON.stringify(error));
            },
            dataType: 'jsonp'
        });
    }

    getFeeds(0);

    /*
     *  Clock - D
     */
    setInterval(
        function updateClock ( )
        {
            var currentTime = new Date ( );
            var currentHours = currentTime.getHours ( );
            var currentMinutes = currentTime.getMinutes ( );
            // Pad the minutes and seconds with leading zeros, if required
            currentMinutes = ( currentMinutes < 10 ? "0" : "" ) + currentMinutes;
            // Compose the string for display
            var currentTimeString = currentHours + ":" + currentMinutes;
            // Update the time display
            document.getElementById("clock").firstChild.nodeValue = currentTimeString;
        }
        ,1000);

    /*
     * Weather Plugin - D
     */
    $.simpleWeather({
        location: 'Setubal,',
        woeid: '',
        unit: 'f',
        success: function(weather) {
            html = '<h2><i class="icon-'+weather.code+'"></i> '+weather.alt.temp+'&deg;' + '</h2>';

            $("#weather").html(html);
        },
        error: function(error) {
            $("#weather").html('<p>'+error+'</p>');
        }
    });
});