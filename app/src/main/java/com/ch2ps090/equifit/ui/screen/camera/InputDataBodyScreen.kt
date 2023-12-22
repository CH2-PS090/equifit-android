package com.ch2ps090.equifit.ui.screen.camera

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.FileProvider
import androidx.core.net.toFile
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.ch2ps090.equifit.BuildConfig
import com.ch2ps090.equifit.R
import com.ch2ps090.equifit.data.response.Predictions
import com.ch2ps090.equifit.di.Injection
import com.ch2ps090.equifit.theme.Dark2
import com.ch2ps090.equifit.theme.Dark3
import com.ch2ps090.equifit.theme.Primary
import com.ch2ps090.equifit.theme.White
import com.ch2ps090.equifit.theme.subTitleLargeIntegralRegular
import com.ch2ps090.equifit.theme.textBodyRegularOpenSans
import com.ch2ps090.equifit.theme.textBodySemiBoldOpenSans
import com.ch2ps090.equifit.ui.common.UiState
import com.ch2ps090.equifit.ui.common.ViewModelFactory
import com.ch2ps090.equifit.ui.components.ButtonPrimary
import com.ch2ps090.equifit.ui.components.ButtonPrimaryFullWidth
import com.ch2ps090.equifit.ui.navigation.Screen
import com.ch2ps090.equifit.ui.screen.auth.register.LoadingIndicator
import com.ch2ps090.equifit.ui.screen.auth.register.RegisterContent
import com.ch2ps090.equifit.ui.screen.auth.register.RegisterViewModel
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Date

@Composable
fun InputDataBodyScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    val viewModel: CameraViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository(LocalContext.current))
    )

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { contentPadding ->
        Box(
            modifier = modifier
                .padding(contentPadding)
                .background(Dark2)
                .fillMaxSize(),
        ) {
            viewModel.uiState.collectAsState().value.let { uiState ->
                when (uiState) {
                    is UiState.Waiting -> {
                        InputDataBodyContent(
                            navController = navController,
                            viewModel = viewModel
                        )
                    }
                    is UiState.Loading -> {
                        Box(
                            modifier = modifier
                                .background(Dark2)
                                .fillMaxSize(),
                            contentAlignment = Alignment.Center,
                        ) {
                            LoadingIndicator()
                        }
                    }
                    is UiState.Success -> {
                        scope.launch {
                            snackbarHostState.showSnackbar("Predict Success")
                        }
                        uiState.data.predictions?.let {
                            PredictionResult(
                                navController = navController,
                                prediction = it
                            )
                        }
                    }
                    is UiState.Error -> {
                        scope.launch {
                            snackbarHostState.showSnackbar("Predict Failed: ${uiState.errorMessage}")
                        }
                        InputDataBodyContent(
                            navController = navController,
                            viewModel = viewModel
                        )
                    }

                    else -> {}
                }
            }
        }
    }
}

@Composable
fun InputDataBodyContent(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: CameraViewModel
) {
    var selectedImages by remember { mutableStateOf<Uri?>(null) }

    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri -> selectedImages = uri }
    )

    var mExpanded by remember { mutableStateOf(false) }
    val mGender = listOf("male", "female")
    var mTextFieldSize by remember { mutableStateOf(Size.Zero)}
    val icon = if (mExpanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown

    var gender by remember { mutableStateOf("") }
    var height by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .padding(
                start = 20.dp,
                end = 20.dp,
                top = 40.dp,
            )
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(bottom = 40.dp)
                .fillMaxWidth(),
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_circle_arrow_left),
                contentDescription = null,
                modifier = Modifier.clickable {
                    navController.navigate(Screen.Camera.route)
                }
            )
            Spacer(modifier = Modifier.width(24.dp))
            Text(
                text = "Data Body",
                style = subTitleLargeIntegralRegular,
                color = White
            )
        }
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxWidth(),
            horizontalAlignment = Alignment.Start,
        ) {
            ImagePreview(selectedImages = selectedImages)
            Spacer(modifier = Modifier.height(20.dp))
            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                ButtonPrimary(
                    text = "Galeri",
                    modifier = Modifier.weight(1f),
                    onClick = {
                        singlePhotoPickerLauncher.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    }
                )
                Spacer(modifier = Modifier.width(8.dp))
                ButtonPrimary(
                    text = "Camera",
                    modifier = Modifier.weight(1f),
                    onClick = {
                    }
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            OutlinedTextField(
                value = gender,
                onValueChange = { gender = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .onGloballyPositioned { coordinates ->
                        mTextFieldSize = coordinates.size.toSize()
                    },
                label = {
                    Text(
                        text = "Gender",
                        style = textBodyRegularOpenSans,
                        color = White
                    )
                },
                placeholder = {
                    Text(
                        text = "Enter your gender",
                        style = textBodyRegularOpenSans,
                    )
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    textColor = White,
                    placeholderColor = White,
                    focusedBorderColor = White,
                    unfocusedBorderColor = White,
                    cursorColor = White,
                    focusedLabelColor = White,
                ),
                trailingIcon = {
                    Icon(
                        icon,
                        "contentDescription",
                        Modifier.clickable { mExpanded = !mExpanded },
                        tint = White
                    )
                }
            )
            DropdownMenu(
                expanded = mExpanded,
                onDismissRequest = { mExpanded = false },
                modifier = Modifier
                    .background(Dark3)
                    .width(with(LocalDensity.current) { mTextFieldSize.width.toDp() })
            ) {
                mGender.forEach { label ->
                    DropdownMenuItem(onClick = {
                        gender = label
                        mExpanded = false
                    }) {
                        Text(
                            text = label,
                            style = textBodyRegularOpenSans,
                            color = White
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            OutlinedTextField(
                value = height,
                onValueChange = { height = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp),
                label = {
                    Text(
                        text = "Height",
                        style = textBodyRegularOpenSans,
                        color = White
                    )
                },
                placeholder = {
                    Text(
                        text = "Enter your height",
                        style = textBodyRegularOpenSans,
                    )
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    textColor = White,
                    placeholderColor = White,
                    focusedBorderColor = White,
                    unfocusedBorderColor = White,
                    cursorColor = White,
                    focusedLabelColor = White,
                )
            )
            Spacer(modifier = Modifier.height(20.dp))
            OutlinedTextField(
                value = weight,
                onValueChange = { weight = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp),
                label = {
                    Text(
                        text = "Weight",
                        style = textBodyRegularOpenSans,
                        color = White
                    )
                },
                placeholder = {
                    Text(
                        text = "Enter your weight",
                        style = textBodyRegularOpenSans,
                    )
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    textColor = White,
                    placeholderColor = White,
                    focusedBorderColor = White,
                    unfocusedBorderColor = White,
                    cursorColor = White,
                    focusedLabelColor = White,
                )
            )
            Spacer(modifier = Modifier.height(20.dp))
            OutlinedTextField(
                value = age,
                onValueChange = { age = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp),
                label = {
                    Text(
                        text = "Age",
                        style = textBodyRegularOpenSans,
                        color = White
                    )
                },
                placeholder = {
                    Text(
                        text = "Enter your age",
                        style = textBodyRegularOpenSans,
                    )
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    textColor = White,
                    placeholderColor = White,
                    focusedBorderColor = White,
                    unfocusedBorderColor = White,
                    cursorColor = White,
                    focusedLabelColor = White,
                )
            )
            ButtonPrimaryFullWidth(
                text = "Submit",
                modifier = Modifier.padding(vertical = 50.dp),
                onClick = {
                    selectedImages?.let { uri ->
                        val bitmap = BitmapFactory.decodeStream(context.contentResolver.openInputStream(uri))
                        val file = context.createImageFile()
                        val out = FileOutputStream(file)
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
                        out.flush()
                        out.close()
                        selectedImages = Uri.fromFile(file)
                    }

                    selectedImages?.let { uri ->
                        val requestFile = uri.toFile().asRequestBody("image/*".toMediaTypeOrNull())
                        val imagePart = requestFile?.let {
                            MultipartBody.Part.createFormData("file", "image.jpg", it)
                        }
                        val requestGender = gender.toRequestBody("text/plain".toMediaType())
                        val requestHeight = height.toRequestBody("text/plain".toMediaType())
                        val requestWeight = weight.toRequestBody("text/plain".toMediaType())
                        val requestAge = age.toRequestBody("text/plain".toMediaType())

                        imagePart?.let {
                            viewModel.predict(
                                it,
                                requestGender,
                                requestHeight,
                                requestWeight,
                                requestAge
                            )
                        }
                    }
                }
            )
        }
    }
}

fun Context.createImageFile(): File {
    // Create an image file name
    val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
    val imageFileName = "JPEG_" + timeStamp + "_"
    val image = File.createTempFile(
        imageFileName, /* prefix */
        ".jpg", /* suffix */
        externalCacheDir      /* directory */
    )
    return image
}

fun Uri.toFile(context: Context): File {
    val filePath = if (scheme == "content") {
        val cursor = context.contentResolver.query(this, null, null, null, null)
        cursor?.use {
            it.moveToFirst()
            val index = it.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
            if (index != -1) {
                it.getString(index)
            } else {
                null
            }
        }
    } else {
        path
    }
    return File(filePath ?: "")
}

@Composable
fun ImagePreview(
    modifier: Modifier = Modifier,
    selectedImages: Uri?
) {
    if (selectedImages == null) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp),
            painter = painterResource(id = R.drawable.thumbnail_img),
            contentDescription = "Preview Image",
        )
    } else {
        AsyncImage(
            model = selectedImages,
            contentDescription = null,
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.Fit
        )
    }
}

@Composable
fun PredictionResult(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    prediction: Predictions
) {
    Box(
        modifier = modifier
            .background(Dark2)
            .fillMaxSize(),
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 20.dp, vertical = 40.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_circle_arrow_left),
                    contentDescription = null,
                    modifier = Modifier.clickable {
                        navController.navigate(Screen.Camera.route)
                    }
                )
                Spacer(modifier = Modifier.width(24.dp))
                Text(
                    text = "Prediction Result",
                    style = subTitleLargeIntegralRegular,
                    color = White
                )
            }
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.Start,
            ) {
                Text(
                    text = "Ankle",
                    style = textBodySemiBoldOpenSans,
                    color = Primary
                )
                Spacer(modifier = Modifier.height(8.dp))
                prediction.ankle?.let {
                    Text(
                        text = it.toString(),
                        style = textBodyRegularOpenSans,
                        color = White
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Arm Length",
                    style = textBodySemiBoldOpenSans,
                    color = Primary
                )
                Spacer(modifier = Modifier.height(8.dp))
                prediction.armLength?.let {
                    Text(
                        text = it.toString(),
                        style = textBodyRegularOpenSans,
                        color = White
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Bicep",
                    style = textBodySemiBoldOpenSans,
                    color = Primary
                )
                Spacer(modifier = Modifier.height(8.dp))
                prediction.bicep?.let {
                    Text(
                        text = it.toString(),
                        style = textBodyRegularOpenSans,
                        color = White
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Body Fat",
                    style = textBodySemiBoldOpenSans,
                    color = Primary
                )
                Spacer(modifier = Modifier.height(8.dp))
                prediction.bodyfat?.let {
                    Text(
                        text = it.toString(),
                        style = textBodyRegularOpenSans,
                        color = White
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Calf",
                    style = textBodySemiBoldOpenSans,
                    color = Primary
                )
                Spacer(modifier = Modifier.height(8.dp))
                prediction.calf?.let {
                    Text(
                        text = it.toString(),
                        style = textBodyRegularOpenSans,
                        color = White
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Chest",
                    style = textBodySemiBoldOpenSans,
                    color = Primary
                )
                Spacer(modifier = Modifier.height(8.dp))
                prediction.chest?.let {
                    Text(
                        text = it.toString(),
                        style = textBodyRegularOpenSans,
                        color = White
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Fore Arm",
                    style = textBodySemiBoldOpenSans,
                    color = Primary
                )
                Spacer(modifier = Modifier.height(8.dp))
                prediction.forearm?.let {
                    Text(
                        text = it.toString(),
                        style = textBodyRegularOpenSans,
                        color = White
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Hip",
                    style = textBodySemiBoldOpenSans,
                    color = Primary
                )
                Spacer(modifier = Modifier.height(8.dp))
                prediction.hip?.let {
                    Text(
                        text = it.toString(),
                        style = textBodyRegularOpenSans,
                        color = White
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Leg Length",
                    style = textBodySemiBoldOpenSans,
                    color = Primary
                )
                Spacer(modifier = Modifier.height(8.dp))
                prediction.legLength?.let {
                    Text(
                        text = it.toString(),
                        style = textBodyRegularOpenSans,
                        color = White
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Neck",
                    style = textBodySemiBoldOpenSans,
                    color = Primary
                )
                Spacer(modifier = Modifier.height(8.dp))
                prediction.neck?.let {
                    Text(
                        text = it.toString(),
                        style = textBodyRegularOpenSans,
                        color = White
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Shoulder Breadth",
                    style = textBodySemiBoldOpenSans,
                    color = Primary
                )
                Spacer(modifier = Modifier.height(8.dp))
                prediction.shoulderBreadth?.let {
                    Text(
                        text = it.toString(),
                        style = textBodyRegularOpenSans,
                        color = White
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Shoulder To Crotch",
                    style = textBodySemiBoldOpenSans,
                    color = Primary
                )
                Spacer(modifier = Modifier.height(8.dp))
                prediction.shoulderToCrotch?.let {
                    Text(
                        text = it.toString(),
                        style = textBodyRegularOpenSans,
                        color = White
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Thigh",
                    style = textBodySemiBoldOpenSans,
                    color = Primary
                )
                Spacer(modifier = Modifier.height(8.dp))
                prediction.thigh?.let {
                    Text(
                        text = it.toString(),
                        style = textBodyRegularOpenSans,
                        color = White
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Waist",
                    style = textBodySemiBoldOpenSans,
                    color = Primary
                )
                Spacer(modifier = Modifier.height(8.dp))
                prediction.waist?.let {
                    Text(
                        text = it.toString(),
                        style = textBodyRegularOpenSans,
                        color = White
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Wrist",
                    style = textBodySemiBoldOpenSans,
                    color = Primary
                )
                Spacer(modifier = Modifier.height(8.dp))
                prediction.wrist?.let {
                    Text(
                        text = it.toString(),
                        style = textBodyRegularOpenSans,
                        color = White
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun InputDataBodyScreenPreview() {
    InputDataBodyScreen(navController = rememberNavController())
}

@Preview
@Composable
fun PredictionResultPreview() {
    PredictionResult(
        navController = rememberNavController(),
        prediction = Predictions()
    )
}