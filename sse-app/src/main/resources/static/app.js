$(() => {
    const evtSource = new EventSource("http://localhost:8080/comments/getnew");
    const eventList = document.querySelector('ul');

    evtSource.onmessage = function (e) {
        const newElement = document.createElement("li");

        newElement.textContent = "message: " + e.data;
        eventList.appendChild(newElement);
    }
});