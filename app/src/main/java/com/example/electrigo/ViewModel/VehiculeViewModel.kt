package com.example.electrigo.ViewModel

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.electrigo.Adapter.logoAdapter
import com.example.electrigo.Adapter.VehiculeAdapter

import com.example.electrigo.Model.Logo
import com.example.electrigo.Model.Vehicule
import com.example.electrigo.R

class VehiculeViewModel : AppCompatActivity() {

    private lateinit var recyclerViewVehicules: RecyclerView
    private lateinit var recyclerViewLogos: RecyclerView
    private lateinit var vehiculesList: ArrayList<Vehicule>
    private lateinit var logosList: ArrayList<Logo>

    private lateinit var vehiculeAdapter: VehiculeAdapter
    private lateinit var logoAdapter: logoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.vehicule_store_view)

        val buttonAddVehicule: Button = findViewById(R.id.buttonaddVehicule)


        // Ajouter un écouteur de clic au bouton
        buttonAddVehicule.setOnClickListener {

            val intent = Intent(this, formulaireVehiculeActivity::class.java)
            startActivity(intent)
        }

        recyclerViewVehicules = findViewById(R.id.recyclerViewVehicules)
        recyclerViewLogos = findViewById(R.id.recyclerviewlogo)

        recyclerViewVehicules.layoutManager = LinearLayoutManager(this)
        vehiculesList = ArrayList()

        vehiculesList.add(Vehicule(R.drawable.tesla, "TESLA", "03", "Automatique", "$150", 2, 220, 50,"Le Tesla Model 3 incarne l'avenir de la conduite électrique avec son mélange captivant de performances, d'efficacité et de technologie avancée. Cette berline électrique élégante offre une autonomie impressionnante grâce à sa batterie innovante, vous permettant de parcourir les routes avec une conscience écologique. L'accélération rapide, associée à des fonctionnalités autonomes de pointe, crée une expérience de conduite exceptionnelle."))
        vehiculesList.add(Vehicule(R.drawable.audi, "AUDI", "A4", "Manuelle", "$120", 5, 180, 45,"L'Audi A4, symbole d'élégance et de performances, incarne le raffinement dans le monde des berlines. Dotée d'un design extérieur saisissant et d'une attention méticuleuse aux détails, l'Audi A4 offre une expérience de conduite sophistiquée. Sous le capot, une gamme de motorisations puissantes assure des performances dynamiques, tandis que l'intérieur luxueux est conçu pour offrir un confort exceptionnel."))
        vehiculesList.add(Vehicule(R.drawable.cupra, "CUPRA", "Leon", "Automatique", "$200", 5, 200, 60,"La CUPRA Leon, une véritable incarnation de la sportivité, fusionne performance et design audacieux pour créer une expérience de conduite exceptionnelle. Arborant un extérieur dynamique avec des lignes agressives, la CUPRA Leon se distingue par son caractère sportif affirméSous le capot, un moteur puissant délivre des performances époustouflantes, offrant une conduite réactive et immersive. L'intérieur est conçu avec soin, alliant confort et technologie de pointe pour créer un environnement de conduite haut de gamme."))
        vehiculesList.add(Vehicule(R.drawable.polo, "POLO", "GTI", "Manuelle", "$200", 5, 250, 55,"La Volkswagen Polo incarne l'alliance parfaite entre élégance et agilité. Compacte et stylée, cette voiture urbaine offre une conduite dynamique associée à un design moderne. Dotée de lignes épurées et d'une silhouette énergique, la Polo attire les regards partout où elle va Sous le capot, la Polo délivre des performances fiables et efficaces, idéales pour une conduite en milieu urbain. Son intérieur est conçu pour offrir confort et fonctionnalité, avec des équipements modernes qui améliorent l'expérience de conduite"))
        vehiculesList.add(Vehicule(R.drawable.porche, "PORCHE", "911 Carrera", "Automatique", "$300", 5, 260, 70,"La Porsche 911 Carrera, icône parmi les sportives, incarne l'essence même de la performance et du luxe. Avec un design intemporel, la 911 Carrera est reconnue pour ses lignes distinctives, sa silhouette élégante et ses phares ronds emblématiques" + "Sous le capot, cette Porsche abrite un moteur puissant qui délivre des performances exceptionnelles. La 911 Carrera excelle sur route, offrant une expérience de conduite dynamique avec une maniabilité précise et une accélération fulgurante."))

        vehiculesList.add(Vehicule(R.drawable.hyundai, "HYUNDAI", "Kona Electric", "Manuelle", "$150", 5, 230, 40,"Le Hyundai Kona Electric représente l'avenir de la conduite électrique, alliant un design audacieux à une technologie de pointe pour une expérience de conduite exceptionnelle. Ce SUV compact électrique offre une autonomie impressionnante sans compromettre les performances.\n" +
                "\n" +
                "Le design extérieur du Kona Electric attire le regard avec des lignes modernes, des accents aérodynamiques et une calandre distinctive. Sous le capot, son groupe motopropulseur électrique délivre une puissance instantanée, assurant des déplacements silencieux et respectueux de l'environnement.\n" +
                "\n" +
                "L'intérieur du Kona Electric propose un mélange de confort et de technologies avancées. L'habitacle bien conçu offre un espace généreux, tandis que l'écran tactile central permet un accès facile aux fonctionnalités de divertissement et de navigation"))
        vehiculesList.add(Vehicule(R.drawable.land_rover, "LandRover", "Discovery Sport", "Automatique", "$250", 5, 320, 80,"Le Land Rover Discovery Sport incarne l'aventure et le luxe dans le monde des SUV compacts. Conçu pour les conducteurs qui recherchent à la fois le raffinement en ville et la robustesse hors route, le Discovery Sport offre une expérience polyvalente,Son extérieur affiche le design emblématique de Land Rover, avec des lignes élégantes et une présence imposante. Ce SUV est équipé pour affronter divers terrains grâce à ses capacités tout-terrain avancées, faisant de chaque trajet une aventure"))
        vehiculesList.add(Vehicule(R.drawable.fiat, "FIAT", " 500X", "Manuelle", "$200", 5, 160, 35,"La Fiat 500X incarne l'esprit audacieux de la marque italienne tout en offrant une polyvalence moderne. Ce SUV compact combine un design distinctif, une conduite agile en ville et des fonctionnalités innovantes"))
        vehiculesList.add(Vehicule(R.drawable.toyota,"TOYOTA", "Camry", "Automatique", "$250", 5, 200, 55,"La Toyota Camry incarne l'excellence japonaise en matière d'automobile depuis des décennies. Cette berline intermédiaire est reconnue pour son équilibre entre performances, confort et fiabilité"))



        vehiculeAdapter = VehiculeAdapter(vehiculesList)
        recyclerViewVehicules.adapter = vehiculeAdapter


//appel a  la  page  details
        vehiculeAdapter.onItemClick = {

            val  intent =Intent(this , DetailsVehiculeActivity ::class.java)
            intent.putExtra("vehicule" , it)
            startActivity(intent)
        }



        // Use LinearLayoutManager with horizontal orientation
        recyclerViewVehicules.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        recyclerViewLogos.layoutManager = LinearLayoutManager(this)
        logosList = ArrayList()
        // Ajoutez vos logos à la liste
        logosList.add(Logo(R.drawable.bmwlogo))
        logosList.add(Logo(R.drawable.jaguarlogo))
        logosList.add(Logo(R.drawable.fiatlogo))
        logosList.add(Logo(R.drawable.toyotalogo))
        logosList.add(Logo(R.drawable.hyundailogo))
        logosList.add(Logo(R.drawable.bentleylogo))
        logosList.add(Logo(R.drawable.fordlogo))





        logoAdapter = logoAdapter(logosList)
        recyclerViewLogos.adapter = logoAdapter

        // Use LinearLayoutManager with horizontal orientation
        recyclerViewLogos.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    }
}





