$(() => {
    const evtSource = new EventSource("http://localhost:8080/comments/getnew");
    const eventList = document.getElementById('comments');

    evtSource.onmessage = function (e) {

        const comment = JSON.parse(e.data)

        console.log(comment)


        const header = document.createElement("div");
        const emptyDiv = document.createElement("div");
        addClassToEl(header, "d-flex", "flex-start", "align-items-center")
        const date = document.createElement("p");
        addClassToEl(date, "text-muted", "small", "mb-0");
        const username = document.createElement("h6");
        addClassToEl(username, "fw-bold", "text-primary", "mb-1");
        const message = document.createElement("p");
        addClassToEl(message, "mt-3", "mb-4", "pb-2", "border-bottom")

        date.textContent = "commented on: " + comment.date;
        username.textContent = comment.name;
        message.textContent = comment.message;
        emptyDiv.append(username)
        emptyDiv.append(date)
        header.append(emptyDiv)
        eventList.prepend(message);
        eventList.prepend(header)
    }

    $("#post-comment").click(function (ev) {
        ev.preventDefault();

        var comment = {};
        comment.message = document.getElementById('comment-message').value;


        // var url = form.attr("action");
        var url = "http://localhost:8088/api/v1/comments/add";
        $.ajax({
            method: "POST",
            url: url,
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify(comment),
            success: function (data) {
                // Ajax call completed successfully
                document.getElementById("comment-message").value = "";
            },
            error: function (data) {
                // Some error in ajax call
                alert("some Error: " + data);
            },
        });
    });


    $("#comments").scroll(function (event) {

        const obj = document.getElementById("comments")
        if (obj.scrollHeight - obj.scrollTop === obj.clientHeight) {

            console.log("scrolled down")
        }

    });

    function addClassToEl() {
        element = arguments[0];
        for (var i = 1; i < arguments.length; i++) {
            element.classList.add(arguments[i])
        }
    }

});