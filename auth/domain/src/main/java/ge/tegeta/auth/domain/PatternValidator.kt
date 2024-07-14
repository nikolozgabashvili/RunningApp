package ge.tegeta.auth.domain

interface PatternValidator {
    fun matches( value: String): Boolean
}