package com.example.le_androidapp.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.le_androidapp.R
import com.example.le_androidapp.databinding.FragmentArticlesBinding

class ArticleFragment : Fragment() {

    private var _binding : FragmentArticlesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentArticlesBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding){
            article1.setOnClickListener {
                val intent = Intent()
                intent.action = Intent.ACTION_VIEW
                intent.addCategory(Intent.CATEGORY_BROWSABLE)
                intent.data = Uri.parse("https://www.usa.edu/blog/how-to-improve-posture/")
                startActivity(intent)
            }

            article2.setOnClickListener {
                directToBrowser("https://www.health.harvard.edu/staying-healthy/is-it-too-late-to-save-your-posture")
            }

            article3.setOnClickListener {
                directToBrowser("https://my.clevelandclinic.org/health/treatments/21033-chiropractic-adjustment")
            }

            article4.setOnClickListener {
                directToBrowser("https://www.medicalnewstoday.com/articles/160645")
            }

            emergency.setOnClickListener {
                findNavController().navigate(R.id.actionArticlesToEmergencyHotline)
            }

            trivia.setOnClickListener {
                directToBrowser("https://alignedmodernhealth.com/8-facts-know-posture/")
            }
        }
    }

    private fun directToBrowser(url :String){
        val intent = Intent()
        intent.action = Intent.ACTION_VIEW
        intent.addCategory(Intent.CATEGORY_BROWSABLE)
        intent.data = Uri.parse(url)
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}