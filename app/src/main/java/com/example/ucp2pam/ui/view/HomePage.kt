package com.example.ucp2pam.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.ucp2pam.R

@Composable
fun HomePage(
    dokterPageButton: () -> Unit,
    jadwalPageButton: () -> Unit,
){
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(
                id = R.drawable.images
            ),
            contentDescription = "",
            modifier = Modifier
                .size(300.dp)
        )
        Spacer(modifier = Modifier.padding(16.dp))
        Button(
            onClick = {
                dokterPageButton()
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(id = R.color.purple_200),
                contentColor = Color.White
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp)
        ) {
            Text(" Dokter")
        }
        Button(
            onClick = {
                jadwalPageButton()
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(id = R.color.purple_200),
                contentColor = Color.White
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp)
        ) {
            Text(" Jadwal")
        }
    }
}
