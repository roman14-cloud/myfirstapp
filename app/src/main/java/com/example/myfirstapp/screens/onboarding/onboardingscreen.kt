
package com.example.myfirstapp.screens.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.myfirstapp.models.onboardingItem
import com.example.myfirstapp.navigations.ROUTE_LOGIN
import com.example.myfirstapp.navigations.ROUTE_ONBOARDING
import kotlinx.coroutines.launch

@Composable
fun onboardingScreen(navController: NavHostController) {
    var pagerState = rememberPagerState(pageCount = { onboardingItem.size })
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        //show skip if not in the last page
        if (pagerState.currentPage != onboardingItem.lastIndex) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(
                    onClick = {
                        navController.navigate(ROUTE_LOGIN) {
                            popUpTo(ROUTE_ONBOARDING) {
                                inclusive = true
                            }
                        }
                    }, modifier = Modifier
                        .height(50.dp)
                        .width(100.dp)){
                            Text(text = "Skip",fontSize=18.sp, color = Color.Blue)}
                }
            }
        }
    Spacer(modifier = Modifier.height(10.dp))
        //pages
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
        ) { page ->
            val item = onboardingItem[page]

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // image
                Image(
                    painter = painterResource(id = item.imageRes),
                    contentDescription = item.title,
                    modifier = Modifier.size(150.dp)
                )

                Spacer(modifier = Modifier.height(10.dp))

                //title
                Text(
                    text = item.title,
                    fontSize = 20.sp,
                    color = Color.Blue,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(10.dp))

                //description
                Text(
                    text = item.description,
                    fontSize = 16.sp,
                    color = Color.Gray,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )

                //indicator
                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    repeat(onboardingItem.size) { index ->
                        val isselected = pagerState.currentPage == index

                        Box(
                            modifier = Modifier
                                .padding(4.dp)
                                .size(if (isselected) 20.dp else 10.dp)
                                .clip(CircleShape)
                                .background(
                                    if (isselected) Color.Blue else Color.Gray
                                )
                        )

                        // Get stated button
                        Button(
                            onClick = {
                                if (pagerState.currentPage == onboardingItem.size - 1) {
                                    scope.launch {
                                        pagerState.animateScrollToPage(
                                            pagerState.currentPage + 1
                                        )
                                    }
                                } else {
                                    navController.navigate(ROUTE_LOGIN)
                                }
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = if (
                                    pagerState.currentPage == onboardingItem.size - 1
                                ) "Get Started" else "Next"
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))
    }


@Preview(showBackground = true)
@Composable
fun onboardingScreenPreview() {
    onboardingScreen(rememberNavController())
}
