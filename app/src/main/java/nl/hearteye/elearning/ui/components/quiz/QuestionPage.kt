package nl.hearteye.elearning.ui.components.quiz

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import nl.hearteye.elearning.data.model.Content
import nl.hearteye.elearning.data.model.QuestionDetail
import nl.hearteye.elearning.ui.components.buttons.OutlinedButton
import nl.hearteye.elearning.ui.components.buttons.RegularButton
import nl.hearteye.elearning.ui.components.quiz.answerbar.AnswerBar
import nl.hearteye.elearning.ui.components.quiz.progressbar.ProgressBar
import nl.hearteye.elearning.ui.theme.typography

@Composable
fun QuestionPage(
    question: QuestionDetail,
    currentQuestionIndex: Int,
    totalQuestions: Int,
    onNext: () -> Unit,
    onBack: () -> Unit,
    canGoBack: Boolean,
    canGoNext: Boolean,
    onSubmitAnswer: (String) -> Unit,
    onCompleteQuiz: () -> Unit,
    image: Content?
) {
    val selectedAnswer = remember { mutableStateOf<String?>(null) }
    val progress = (currentQuestionIndex + 1).toFloat() / totalQuestions
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        ProgressBar(
            currentQuestion = currentQuestionIndex + 1,
            totalQuestions = totalQuestions,
            progress = progress
        )

        Spacer(modifier = Modifier.height(8.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(5.dp, shape = RoundedCornerShape(10.dp))
                .clip(RoundedCornerShape(10.dp))
                .background(Color.White, shape = RoundedCornerShape(10.dp))
                .padding(16.dp)
                .heightIn(min = 125.dp)
        ) {
            Text(
                text = question.question,
                style = typography.bodyLarge
            )
            image?.let {
                Image(
                    painter = rememberImagePainter(image.sasUrl),
                    contentDescription = "Quiz Image",
                    modifier = Modifier
                        .width(175.dp)
                        .align(Alignment.CenterHorizontally)
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))
        Spacer(modifier = Modifier.height(12.dp))

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(5.dp, shape = RoundedCornerShape(10.dp))
                .clip(RoundedCornerShape(10.dp))
                .background(Color.White, shape = RoundedCornerShape(10.dp))
                .padding(16.dp)
        ) {
            item {
                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    question.answers.forEach { answer ->
                        AnswerBar(
                            id = answer.id,
                            answer = answer.content,
                            isSelected = selectedAnswer.value == answer.id,
                            onCheckedChange = { isSelected ->
                                if (isSelected) {
                                    selectedAnswer.value = answer.id
                                } else {
                                    selectedAnswer.value = null
                                }
                            }
                        )
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (canGoBack) {
                        OutlinedButton(
                            onClick = onBack,
                            text = "Back",
                            modifier = Modifier.weight(1f)
                        )
                    } else {
                        Spacer(modifier = Modifier.weight(1f))
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    RegularButton(
                        onClick = {
                            selectedAnswer.value?.let { answerId ->
                                onSubmitAnswer(answerId)

                                if (currentQuestionIndex == totalQuestions - 1) {
                                    onCompleteQuiz()
                                } else {
                                    onNext()
                                }
                            }
                        },
                        text = if (currentQuestionIndex == totalQuestions - 1) "Submit" else "Next",
                        enabled = selectedAnswer.value != null,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}