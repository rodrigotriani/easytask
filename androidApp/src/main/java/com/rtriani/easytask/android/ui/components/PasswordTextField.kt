import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rtriani.easytask.android.R

@Composable
fun PasswordTextField(
    placeholder: String = "",
    label: String = "",
    password: String = "",
    onValueChange: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        var passwordVisibility by remember { mutableStateOf(false) }

        val icon = if (passwordVisibility) {
            painterResource(id = R.drawable.design_ic_visibility)
        } else {
            painterResource(id = R.drawable.design_ic_visibility_off)
        }

        TextField(
            modifier = modifier,
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = Color.Transparent
            ),
            value = password,
            onValueChange = onValueChange,
            placeholder = {
                Text(text = placeholder)
            },
            label = {
                Text(text = label)
            },
            trailingIcon = {
                IconButton(onClick = {
                    passwordVisibility = !passwordVisibility
                }) {
                    Icon(
                        painter = icon,
                        contentDescription = ""
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            ),
            visualTransformation = if (passwordVisibility){
                VisualTransformation.None
            }else{
                PasswordVisualTransformation()
            }
        )

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
@Preview
fun PasswordTextFieldPreview() {
    Column(
        modifier = Modifier
            .fillMaxWidth(1f)
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        PasswordTextField(
            placeholder = "Senha",
            label = "Senha",
            password = "",
            onValueChange = {},
            modifier = Modifier
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
    }
}