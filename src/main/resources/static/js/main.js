$(document).ready(function () {
    $('.pass_show').append('<span class="ptxt" ><i class="bi bi-eye-slash" id="togglePassword"></i></span>');
});

$(document).on('click', '#togglePassword', function () {
    if ($('#floatingPassword').attr("type") == "text") {
        $('#floatingPassword').attr('type', 'password');
        $(this).addClass("bi bi-eye-slash");
        $(this).removeClass("bi bi-eye");
    } else if ($('#floatingPassword').attr("type") == "password") {
        $('#floatingPassword').attr('type', 'text');
        $(this).removeClass("bi bi-eye-slash");
        $(this).addClass("bi bi-eye");
    }
});

