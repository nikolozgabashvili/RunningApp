@file:OptIn(ExperimentalFoundationApi::class)


package ge.tegeta.auth.presentation.register

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ge.tegeta.auth.domain.UserDataValidator
import ge.tegeta.auth.presentation.R
import ge.tegeta.core.presentation.designsystem.CheckIcon
import ge.tegeta.core.presentation.designsystem.CrossIcon
import ge.tegeta.core.presentation.designsystem.EmailIcon
import ge.tegeta.core.presentation.designsystem.Poppins
import ge.tegeta.core.presentation.designsystem.TrackerAppTheme
import ge.tegeta.core.presentation.designsystem.TrackerDarkRed
import ge.tegeta.core.presentation.designsystem.TrackerGray
import ge.tegeta.core.presentation.designsystem.TrackerGreen
import ge.tegeta.core.presentation.designsystem.components.GradientBackground
import ge.tegeta.core.presentation.designsystem.components.RuniqueActionButton
import ge.tegeta.core.presentation.designsystem.components.RuniquePasswordTextField
import ge.tegeta.core.presentation.designsystem.components.RuniqueTextField
import org.koin.androidx.compose.koinViewModel

@Composable

fun RegisterScreenRot(
    viewModel: RegisterViewModel = koinViewModel()
) {

    RegisterScreen(
        state = viewModel.state,
        onAction = viewModel::onAction
    )

}

@Composable
private fun RegisterScreen(
    state: RegisterState,
    onAction: (RegisterAction) -> Unit
) {
    GradientBackground {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .padding(vertical = 32.dp)
                .padding(top = 16.dp),

            ) {
            Text(
                text = stringResource(id = R.string.register),
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.headlineMedium
            )
            val annotatedString = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        fontFamily = Poppins,
                        color = TrackerGray,
                    )
                ) {
                    append(stringResource(id = R.string.already_have_acc) + " ")
                    pushStringAnnotation(
                        tag = "clickable_text",
                        annotation = stringResource(id = R.string.login)
                    )
                    withStyle(
                        style = SpanStyle(
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.primary,
                            fontFamily = Poppins
                        )
                    ) {
                        append(stringResource(id = R.string.login))
                    }

                }

            }
            ClickableText(text = annotatedString, onClick = {
                annotatedString.getStringAnnotations(
                    tag = "clickable_text",
                    start = it,
                    end = it
                ).firstOrNull()?.let {
                    onAction(RegisterAction.OnLoginInClick)
                }
            })
            Spacer(Modifier.height(48.dp))
            RuniqueTextField(
                state = state.email,
                hint = stringResource(id = R.string.example_email),
                startIcon = EmailIcon,
                endIcon = if (state.isEmailValid) {
                    CheckIcon
                } else null,
                title = stringResource(id = R.string.email),
                modifier = Modifier.fillMaxWidth(),
                additionalInfo = stringResource(id = R.string.must_be_valid_email),
            )
            Spacer(modifier = Modifier.height(16.dp))
            RuniquePasswordTextField(
                state = state.password,
                hint = stringResource(id = R.string.password),
                title = stringResource(id = R.string.password),
                modifier = Modifier.fillMaxWidth(),
                onTogglePasswordVisibility = {
                    onAction(RegisterAction.OnTogglePasswordVisibilityCheck)

                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            PasswordRequirement(
                text = stringResource(
                    id = R.string.at_least_x_characters,
                    UserDataValidator.MIN_PASSWORD_LENGTH
                ),
                isValid = state.passwordValidationState.hasMinLength
            )
            Spacer(modifier = Modifier.height(4.dp))
            PasswordRequirement(
                text = stringResource(id = R.string.at_least_one_number),
                isValid = state.passwordValidationState.hasNumber
            )
            Spacer(modifier = Modifier.height(4.dp))
            PasswordRequirement(
                text = stringResource(id = R.string.contains_lowercase_character),
                isValid = state.passwordValidationState.hasLowercase
            )
            Spacer(modifier = Modifier.height(4.dp))
            PasswordRequirement(
                text = stringResource(id = R.string.contains_uppercase_character),
                isValid = state.passwordValidationState.hasUppercase
            )
            Spacer(modifier = Modifier.height(32.dp))
            RuniqueActionButton(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = R.string.register),
                enabled = state.canRegister,
                isLoading = state.isRegistering
            ) {
                onAction(RegisterAction.OnRegisterClick)

            }


        }
    }

}

@Composable
fun PasswordRequirement(
    text: String,
    isValid: Boolean,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = if (isValid) CheckIcon else CrossIcon,
            contentDescription = null,
            tint = if (isValid) TrackerGreen else TrackerDarkRed
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = text,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontSize = 14.sp
        )

    }

}

@Preview
@Composable

private fun RegisterScreenPreview() {

    TrackerAppTheme {

        RegisterScreen(
            state = RegisterState(),

            onAction = {}

        )

    }

}