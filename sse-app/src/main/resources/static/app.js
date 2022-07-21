$(() => {
    const evtSource = new EventSource("http://localhost:8080/comments/getnew");
    const eventList = document.querySelector('ul');

    evtSource.onmessage = function (e) {
        const newElement = document.createElement("li");

        newElement.textContent = "message: " + e.data;
        eventList.appendChild(newElement);

        $("#send").click(function (ev) {
            ev.preventDefault();
            var form = $("#formId");
            // var url = form.attr("action");
            var url = "http://localhost:8088/comments/";
            $.ajax({
                method: "POST",
                url: url,
                data: form.serialize(),
                success: function (data) {
                    // Ajax call completed successfully
                    alert("Form Submited Successfully");
                    document.getElementById("formId").reset();
                },
                error: function (data) {
                    // Some error in ajax call
                    alert("some Error");
                },
            });
        });
    }
});