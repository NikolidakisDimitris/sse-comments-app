$(() => {
    const evtSource = new EventSource("http://localhost:8080/comments/getnew");
    const eventList = document.getElementById('comments');

    evtSource.onmessage = function (e) {

        const comment = JSON.parse(e.data)

        console.log(comment)


        const header = document.createElement("div");
        const emptyDiv = document.createElement("div");
        addClassToEl(header, "d-flex", "flex-start", "align-items-center")
        const timestamp = document.createElement("p");
        addClassToEl(timestamp, "text-muted", "small", "mb-0");    
        const username = document.createElement("h6");
        addClassToEl(username, "fw-bold", "text-primary", "mb-1");
        const message = document.createElement("p");
        addClassToEl(message, "mt-3", "mb-4", "pb-2", "border-bottom")  

        timestamp.textContent = "Time posted: " + comment.timestamp;
        username.textContent = comment.name;
        message.textContent = comment.message;
        emptyDiv.append(username)
        emptyDiv.append(timestamp)
        header.append(emptyDiv)
        // header.appendChild(username);
        // header.appendChild(timestamp);
        eventList.append(header);
        eventList.append(message)

//        $("#send").click(function (ev) {
//            ev.preventDefault();
//            var form = $("#formId");
//            // var url = form.attr("action");
//            var url = "http://localhost:8088/comments/";
//            $.ajax({
//                method: "POST",
//                url: url,
//                data: form.serialize(),
//                success: function (data) {
//                    // Ajax call completed successfully
//                    alert("Form Submited Successfully");
//                    document.getElementById("formId").reset();
//                },
//                error: function (data) {
//                    // Some error in ajax call
//                    alert("some Error");
//                },
//            });
//        });

        function addClassToEl(){
            element = arguments[0];
            for (var i = 1; i < arguments.length; i++){
                element.classList.add(arguments[i])
            }
        }
    }
});