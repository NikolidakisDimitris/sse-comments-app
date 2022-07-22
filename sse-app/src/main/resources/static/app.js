$(() => {

    const commentsList = document.getElementById('comments');
    let pageNumber = 0;

    ajaxGetCommentsPage();

    const evtSource = new EventSource("/view-api/v1/comments/sse");

    evtSource.onmessage = function (e) {

        const comment = JSON.parse(e.data)
        prependComment(comment);
    }

    //must APPEND when getAllComments.

    $("#post-comment").click(function (ev) {
        ev.preventDefault();

        var comment = {};
        comment.message = document.getElementById('comment-message').value;
        comment.name = document.getElementById('user-name').value;

        //insert comment
        var url = "http://localhost:8088/write-api/v1/comments/";
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

    function ajaxGetCommentsPage() {

        var url = "http://localhost:8080//view-api/v1/comments/getAll";

        pageParam = "?page=" + pageNumber;
        url += pageParam;

        $.ajax({
            method: "GET",
            url: url,
            //data is comments in this case
            success: function (data) {

                console.log(data);
                if (data !== undefined && data.length > 0) {

                    scrollElementToMid(commentsList)
                    for (var i = 0; i < data.length; i++) {
                        commentItem = data[i];
                        console.log(commentItem);
                        appendComment(commentItem);
                    }
                    pageNumber += 1;
                } else {
                    console.log("No more comments.")
                }
            },
            error: function (data) {
                console.log("Error calling getAll comments")
            }


        })
    }

    function scrollElementToMid(element) {

        element.scrollTop = ((element.scrollHeight - element.clientHeight) / 2) * 1.6;

    }


    $("#comments").scroll(function (event) {

        const obj = document.getElementById("comments")
        if (obj.scrollHeight - obj.scrollTop === obj.clientHeight) {
            ajaxGetCommentsPage()
            console.log("scrolled down")
        }

    });

    function addClassToEl() {
        element = arguments[0];
        for (var i = 1; i < arguments.length; i++) {
            element.classList.add(arguments[i])
        }
    }


    function prependComment(comment) {

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
        commentsList.prepend(message);
        commentsList.prepend(header)
    }

    function appendComment(comment) {

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
        commentsList.append(header);
        commentsList.append(message)
    }

});