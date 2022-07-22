$(() => {

    //base element to append comments
    const commentsList = document.getElementById('comments');
    //input elements
    const commentMessage = document.getElementById('comment-message');
    const userName = document.getElementById('user-name');
    //pageNumber used for pagination
    let pageNumber = 0;

    //get first 10 comments to load
    ajaxGetCommentsPage();

    const evtSource = new EventSource('view-api/v1/comments/sse');

    evtSource.onmessage = function (e) {

        const comment = JSON.parse(e.data);
        // prependComment(comment);
        let header = createCommentHeader(comment);
        let message = createCommentMessage(comment);
        prependToComments(header, message);


    }

    $('#post-comment').click(function (ev) {
        ev.preventDefault();

        var comment = {
            message: commentMessage.value,
            name: userName.value
        };

        //insert comment
        var url = 'http://localhost:8088/write-api/v1/comments/';
        $.ajax({
            method: 'POST',
            url: url,
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(comment),
            success: function (data) {
                commentMessage.value = '';
            },
            error: function (data) {
                console.log('Error during ajax call: ' + data);
            },
        });
    });


    function ajaxGetCommentsPage() {
        var url = 'http://localhost:8080/view-api/v1/comments/getAll';

        pageParam = '?page=' + pageNumber;
        url += pageParam;

        $.ajax({
            method: 'GET',
            url: url,
            //data is comments in this case
            success: function (data) {

                console.log(data);
                if (data !== undefined && data.length > 0) {

                    setScrollBarPosition(commentsList)
                    for (var i = 0; i < data.length; i++) {
                        commentItem = data[i];
                        console.log(commentItem);
                        let header = createCommentHeader(commentItem);
                        let message = createCommentMessage(commentItem);
                        appendToComments(header, message)
                    }
                    pageNumber += 1;
                } else {
                    console.log('No more comments.');
                }
            },
            error: function (data) {
                console.log('Error calling getAll comments');
            }


        })
    }

    //move the scroll bar after inserting new comments. 
    function setScrollBarPosition(element) {
        element.scrollTop = ((element.scrollHeight - element.clientHeight) / 2) * 1.6;
    }

    //listen for scrolling in the comments div
    $('#comments').scroll(function (event) {
        if (commentsList.scrollHeight - commentsList.scrollTop === commentsList.clientHeight) {
            ajaxGetCommentsPage()
            console.log('scrolled down');
        }
    });

    function addClassToElement(element, cssClasses) {
        for (let cssClass of cssClasses) {
            element.classList.add(cssClass);
        }
    }

    //create the header(name, date) for the comment element
    function createCommentHeader(comment) {

        const header = document.createElement('div');
        const emptyDiv = document.createElement('div');
        addClassToElement(header, ['d-flex', 'flex-start', 'align-items-center'])
        const date = document.createElement('p');
        addClassToElement(date, ['text-muted', 'small', 'mb-0']);
        const username = document.createElement('h6');
        addClassToElement(username, ['fw-bold', 'text-primary', 'mb-1']);


        date.textContent = 'commented on: ' + comment.date;
        username.textContent = comment.name;

        emptyDiv.append(username)
        emptyDiv.append(date)
        header.append(emptyDiv)

        return header;
    }

    //create the message for the comment element
    function createCommentMessage(comment) {
        const message = document.createElement('p');
        addClassToElement(message, ['mt-3', 'mb-4', 'pb-2', 'border-bottom'])
        message.textContent = comment.message;
        return message;
    }

    function appendToComments(header, message) {
        commentsList.append(header);
        commentsList.append(message);
    }

    function prependToComments(header, message) {
        commentsList.prepend(message);
        commentsList.prepend(header);
    }

});