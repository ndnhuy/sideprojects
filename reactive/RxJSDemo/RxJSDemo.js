// var requestStream = Rx.Observable.of(
//   'https://api.github.com/users',
//   'https://api.github.com/events',
//   'https://api.github.com/emojis');

// var refreshClickStream = Rx.Observable.fromEvent($('#refresh'), 'click'),
//   requestStream = refreshClickStream
//         .startWith('startup click')
//         .map(() => {
//           return 'https://dog.ceo/api/breeds/image/random';
//         });
// var responseStream =requestStream.flatMap(requestUrl => Rx.Observable.fromPromise(jQuery.getJSON(requestUrl)));
// var suggestionStream =responseStream.map(response => response.message)
//                   .merge(refreshClickStream.map(click => null));

// var renderAndRemoveStream = suggestionStream.partition(o => o !== null);
// renderAndRemoveStream[0]
//     .subscribe(imageUrl => $('.container')
//     .append('<input type="image" src="' + imageUrl + '" alt="Submit" width="200" height="200">'));
// renderAndRemoveStream[1].subscribe(empty => $('div input').first().remove());
















//############
//###########################################
// var urlStream = refreshClickStream.map(() => "http://localhost:9887/events");
// var responseStream = Rx.Observable.interval(2000).map(() => "http://localhost:9887/events")
// 	.flatMap(url => Rx.Observable.fromPromise(jQuery.getJSON(url)));

// //responseStream.subscribe(result => console.log(JSON.stringify(result)));



// var fluxStream = Rx.Observable.interval(1).map(() => "http://localhost:9887/foo")
// 		.flatMap(url => Rx.Observable.fromPromise(jQuery.getJSON(url)));

// var eventSource = new EventSource('http://localhost:9887/foo');
// eventSource.onmessage = e => console.log(e.data);
// eventSource.onOpen = e => console.log('OPEN');
// eventSource.onerror = function() {
//     console.log("EventSource failed.");
// };

var c = document.getElementById("myCanvas");
var context = c.getContext("2d");

var t = 0;

Rx.Observable.create(observer => {
	var eventSource = new EventSource('http://localhost:9887/events');
	eventSource.onmessage = e => observer.next(e.data);
	eventSource.onerror = e => observer.error('EventSource failed');
	return () => {
		eventSource.close();
	};
})
	.map(json => JSON.parse(json))
	//.delay(5000)
    .bufferTime(3000)
	.flatMap(jsons => Rx.Observable.from(jsons))
	.zip(Rx.Observable.interval(20))
	.map(zipResult => zipResult[0])
	.subscribe(balls => {
		cur = new Date();
		//console.log(cur - t);
		t = cur;
		//console.log(balls.length);
		context.clearRect(0,0,1800,900);
		balls.forEach(b => {
		    context.beginPath();
		    context.fillStyle=b.color;
		    context.arc(b.x, b.y, b.radius, 0, Math.PI*2, true);
		    context.closePath();
		    context.fill();
		});
	}, error => console.log(error), () => console.log('Completed'));


