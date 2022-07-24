$(() => {

    //base element to append comments
    const commentsList = document.getElementById('comments');
    //input elements
    const commentMessage = document.getElementById('comment-message');
    const userName = document.getElementById('user-name');
    //pageNumber used for pagination
    let pageNumber = 0;

    let previousPageNumber = -1;

    // // //comments data objects
    // var commentsArray = [];

    //testing
    // var noInitialComments = false;
    var initialComments = false;

    // after 10 comments from the emitter we need to turn the page (avoid loading duplicate comments)
    var commentsFromEmitterCounter = 0;


    //get first 10 comments to load
    ajaxGetCommentsPage();

    const evtSource = new EventSource('view-api/v1/comments/sse');

    evtSource.onmessage = function (e) {

        const comment = JSON.parse(e.data);
        // prepend a comment
        let header = createCommentHeader(comment);
        let message = createCommentMessage(comment);
        prependToComments(header, message);
        //after 10 comments from the emitter turn the page
        if (commentsFromEmitterCounter >= 10) {
            pageNumber += 1;
            commentsFromEmitterCounter = 0;
        }
        commentsFromEmitterCounter += 1;
    }

    $('#post-comment').click(function (ev) {
        ev.preventDefault();

        var comment = {
            message: commentMessage.value,
            name: userName.value
        };

        //insert comment
        var url = 'http://localhost:8088/write-api/v1/comments/';
        console.log("paranoia 2");
        $.ajax({
            method: 'POST',
            url: url,
            crossDomain: true,
            dataType: "json",
//            headers: {
//                'Access-Control-Allow-Origin': '*',
//                'Access-Control-Allow-Credentials': 'true',
//                'Access-Control-Allow-Methods': 'OPTIONS, GET, POST',
//                'Access-Control-Allow-Headers': 'Content-Type, Depth, User-Agent, X-File-Size, X-Requested-With, If-Modified-Since, X-File-Name, Cache-Control'
//             },
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

                //check if the first call had data
                if (data.length > 0 && !initialComments) {
                    initialComments = true;
                }

                // commentsArray = commentsArray.concat(data);
                // console.log(commentsArray);
                // if (commentsArray.length === 0) {
                //     noInitialComments = true;
                // }




                //add comment to html
                if (data !== undefined && data.length > 0 && initialComments) {

                    setScrollBarPosition(commentsList)
                    for (var i = 0; i < data.length; i++) {
                        commentItem = data[i];

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
                console.log('Error calling getAll comments: ' + data);
            }


        })
    }

    //move the scroll bar after inserting new comments. 
    function setScrollBarPosition(element) {
        element.scrollTop = ((element.scrollHeight - (element.clientHeight)) / 2) * 2;
    }

    //listen for scrolling in the comments div
    $('#comments').scroll(function (event) {
        if (commentsList.scrollHeight - commentsList.scrollTop === commentsList.clientHeight && (pageNumber > previousPageNumber)) {
            previousPageNumber = pageNumber;
            ajaxGetCommentsPage()
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