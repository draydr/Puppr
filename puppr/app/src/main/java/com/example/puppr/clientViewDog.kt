package com.example.puppr


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.get
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideContext
import com.example.puppr.databinding.FragmentClientViewDogBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.firestore.FieldValue

/**
 * A simple [Fragment] subclass.
 */
class clientViewDog : Fragment() {

    private lateinit var binding: FragmentClientViewDogBinding
    private lateinit var userVM: UserViewModel
    private lateinit var currentDogID: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate<FragmentClientViewDogBinding>(inflater, R.layout.fragment_client_view_dog,
            container, false)

        userVM = activity?.run {
            ViewModelProviders.of(this).get(UserViewModel::class.java)
        } ?: throw Exception("Invalid Activity")

        loadDog()

        val bottomNav: BottomNavigationView = binding.viewDogsBottomNav
        bottomNav.selectedItemId = bottomNav.menu[1].itemId
        bottomNav.menu[2].setOnMenuItemClickListener {

            this.findNavController().navigate(R.id.action_clientViewDog_to_clientSavedDogs)
            return@setOnMenuItemClickListener true
        }

        bottomNav.menu[0].setOnMenuItemClickListener {
            this.findNavController().navigate(R.id.action_clientViewDog_to_clientPreferences)
            return@setOnMenuItemClickListener true
        }

        binding.likeButton.setOnClickListener {

            val likedDogsRef = userVM.database.collection("users").document(userVM.userID.toString())
            val likedDogID = userVM.dogID

            likedDogsRef
                .update("likedDogs", FieldValue.arrayUnion(likedDogID))
                .addOnSuccessListener { Log.d("YERT", "DocumentSnapshot successfully updated!") }
                .addOnFailureListener { e -> Log.w("YERT", "Error updating document", e) }
            loadDog(true)
        }

        binding.dislikeButton.setOnClickListener {

            val likedDogsRef = userVM.database.collection("users").document(userVM.userID.toString())
            val likedDogID = userVM.dogID

            likedDogsRef
                .update("dislikedDogs", FieldValue.arrayUnion(likedDogID))
                .addOnSuccessListener { Log.d("YERT", "DocumentSnapshot successfully updated!") }
                .addOnFailureListener { e -> Log.w("YERT", "Error updating document", e) }
            loadDog(true)
        }

        binding.clientDogCard.setOnClickListener {
            this.findNavController().navigate(R.id.action_clientViewDog_to_clientFocusDog)
        }

        return binding.root
    }

    private fun loadDog(newDog: Boolean = false) {

        userVM.savedDogsID = null

        if (newDog) {

            userVM.loadDog()
        }

//        Log.d("YERT", userVM.user.likedDogs.toString())
//        if (!userVM.user.likedDogs.isNullOrEmpty() && !userVM.user.dislikedDogs.isNullOrEmpty()
//            && userVM.user.likedDogs!!.contains(userVM.dogID) or userVM.user.dislikedDogs!!.contains(userVM.dogID)) {
//            loadDog(true)
//            return
//        }

        binding.dogName.text = userVM.dog.name

        Glide.with(this)
            .load(userVM.dog.photo?.get(0))
            .placeholder(R.mipmap.client_base_dog)
            .optionalCenterCrop()
            .into(binding.dogImage)

        binding.shelterName.text = userVM.dog.shelter
        binding.dogName.refreshDrawableState()
        binding.dogImage.refreshDrawableState()
        binding.shelterName.refreshDrawableState()
    }
}
