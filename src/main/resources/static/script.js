document.addEventListener("DOMContentLoaded", function () {
    const studentForm = document.getElementById("student-form");
    const studentList = document.getElementById("student-list");
    const courseForm = document.getElementById("course-form");
    const courseList = document.getElementById("course-list");

    // Fetch and display students
    function loadStudents() {
        fetch("/api/v1/students")
            .then(response => response.json())
            .then(data => {
                studentList.innerHTML = "";
                data.forEach(student => {
                    const li = document.createElement("li");
                    li.textContent = `${student.firstName} ${student.lastName} - ${student.email}`;
                    studentList.appendChild(li);
                });
            });
    }

    // Fetch and display courses
    function loadCourses() {
        fetch("/api/v1/courses")
            .then(response => response.json())
            .then(data => {
                courseList.innerHTML = "";
                data.forEach(course => {
                    const li = document.createElement("li");
                    li.textContent = `${course.name} - ${course.description}`;
                    courseList.appendChild(li);
                });
            });
    }

    studentForm.addEventListener("submit", function (event) {
        event.preventDefault();
        const student = {
            id: null,
            firstName: document.getElementById("firstName").value,
            lastName: document.getElementById("lastName").value,
            dateOfBirth: document.getElementById("dob").value,
            email: document.getElementById("email").value,
            enrollment: document.getElementById("enroll").value
        };

        fetch("/api/v1/students", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(student)
        }).then(() => {
            studentForm.reset();
            loadStudents();
        });
    });

    courseForm.addEventListener("submit", function (event) {
        event.preventDefault();
        const course = {
            name: document.getElementById("courseName").value,
            description: document.getElementById("courseDescription").value
        };

        fetch("/api/v1/courses", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(course)
        }).then(() => {
            courseForm.reset();
            loadCourses();
        });
    });

    loadStudents();
    loadCourses();
});
