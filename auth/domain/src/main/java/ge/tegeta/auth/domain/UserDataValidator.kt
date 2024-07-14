package ge.tegeta.auth.domain

class UserDataValidator(
    private val patternValidator: PatternValidator
) {

    fun isValidEmail(email:String):Boolean{
        return patternValidator.matches(email.trim())
    }

    fun validatePassword(password:String):PasswordValidationState{
        val hadMinLength = password.length>= MIN_PASSWORD_LENGTH
        val hasDigit = password.any { it.isDigit() }
        val hasLowerCase = password.any { it.isLowerCase() }
        val hasUpperCase = password.any { it.isUpperCase() }
        return PasswordValidationState(
            hasMinLength = hadMinLength,
            hasNumber = hasDigit,
            hasLowercase = hasLowerCase,
            hasUppercase = hasUpperCase,
        )
    }

    companion object{
        const val MIN_PASSWORD_LENGTH = 9
    }
}