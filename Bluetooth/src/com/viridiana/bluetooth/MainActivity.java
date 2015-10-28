package com.viridiana.bluetooth;


import java.util.Set;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	Button activarBt, buscarBt, sincronizados, procesar, detener;
	ListView listaBt;
	boolean activar=false;
	Set<BluetoothDevice> dispositivos;
	Bluetooth bt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Asociación de botones
        activarBt = (Button)findViewById(R.id.activar);
        buscarBt = (Button)findViewById(R.id.buscar);
        sincronizados = (Button)findViewById(R.id.buscarSin);
        procesar = (Button)findViewById(R.id.procesar);
        detener = (Button)findViewById(R.id.detener);
        listaBt = (ListView)findViewById(R.id.dispositivos);
        
        bt = new Bluetooth(getApplicationContext(), this);
        //Si el Bluetooth está activado habilita los botones
        activar = bt.verificarBluetooth();
        if (activar){
        	buscarBt.setEnabled(true);
        	sincronizados.setEnabled(true);
        }else{
        	buscarBt.setEnabled(false);
        	sincronizados.setEnabled(false);
        }
        //Botón para activar el Bluetooth
        activarBt.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				activar = bt.activarBluetooth();
				if(activar){
					buscarBt.setEnabled(true);
					sincronizados.setEnabled(true);
					Toast toast = Toast.makeText(getApplicationContext(), "Bluetooth activado", Toast.LENGTH_SHORT);
					toast.show();
				}
			}
		});
        //Botón que busca dispositivos cercanos activados y los muestra en un ListView
        buscarBt.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				if (activar){
					bt.buscarDispositivos();
					listaBt.setAdapter(bt.getEncontrados());
				}
			}
		});
        //Botón que busca dispositivos sincronizados y los muestra en un ListView
        sincronizados.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if(activar){
					dispositivos = bt.obtenerConectados();
					listaBt.setAdapter(bt.getEncontrados());
				}
				
			}
		});
        //botón provisional para procesar señales
        procesar.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				
				//startService(new Intent(getApplicationContext(), Servicio.class));
				/*int M=1000; //número de muestras de la señal (ejemplo)
				double[] abdominal= {3.314637, 2.943371, 2.575639, 2.213838, 1.849213, 1.640780, 1.544709, 1.447901, 1.361641, 1.449003, 1.543704, 1.639693, 1.666912, 1.667195, 1.667177, 1.667349, 1.666942, 1.725699, 1.818725, 1.905028, 1.980134, 2.039492, 2.094488, 2.134118, 2.147933, 2.134655, 2.097168, 2.034603, 1.949822, 1.847385, 1.733344, 1.666968, 1.667553, 1.666953, 1.667141, 1.667375, 1.667231, 1.713894, 1.786967, 1.859035, 1.934574, 2.033632, 2.100650, 2.022938, 1.930680, 1.838305, 1.765798, 1.693683, 1.658571, 1.639889, 1.621154, 1.606799, 1.626418, 1.645867, 1.707928, 1.785462, 1.844419, 1.879876, 1.896937, 1.889439, 1.867327, 1.825356, 1.761035, 1.731728, 1.743248, 1.767533, 1.788025, 1.801741, 1.804783, 1.944296, 2.218469, 2.481920, 2.738192, 2.992149, 3.244674, 2.979014, 2.684440, 2.393768, 2.104753, 1.812908, 1.703947, 1.699055, 1.694147, 1.699983, 1.846729, 1.955786, 1.959402, 1.903808, 1.828108, 1.755587, 1.682785, 1.655918, 1.684066, 1.739477, 1.800457, 1.879432, 1.945750, 1.997414, 2.015226, 2.015006, 1.997974, 1.965135, 1.917273, 1.874487, 1.823980, 1.766114, 1.733217, 1.744714, 1.770564, 1.790984, 1.802530, 1.802852, 1.796044, 1.778461, 1.750640, 1.717026, 1.697896, 1.701386, 1.698417, 1.682662, 1.666820, 1.667237, 1.667222, 1.734351, 1.807494, 1.880481, 1.954546, 2.032843, 2.045310, 2.013971, 2.010983, 1.995117, 1.957846, 1.901851, 1.875507, 1.820283, 1.740702, 1.656776, 1.631785, 1.650957, 1.667464, 1.666550, 1.667630, 1.668495, 1.813692, 2.104986, 2.413523, 2.722739, 3.033041, 3.312723, 3.059002, 2.791421, 2.519863, 2.240567, 1.948581, 1.773669, 1.676957, 1.570661, 1.465677, 1.495740, 1.568316, 1.645436, 1.666950, 1.667200, 1.667094, 1.672350, 1.744837, 1.864866, 2.011633, 2.156517, 2.293693, 2.332440, 2.289955, 2.229691, 2.155338, 2.065336, 1.965096, 1.900940, 1.822011, 1.734157, 1.662200, 1.634228, 1.653722, 1.666904, 1.667135, 1.667434, 1.666980, 1.667166, 1.670610, 1.689384, 1.709551, 1.746700, 1.771207, 1.782669, 1.792904, 1.794862, 1.803647, 1.802411, 1.791817, 1.771308, 1.760185, 1.769624, 1.804853, 1.844452, 1.879881, 1.896922, 1.889439, 1.852920, 1.792680, 1.757910, 1.786794, 1.859536, 1.932791, 2.009962, 2.066873, 1.993027, 2.061720, 2.275750, 2.492380, 2.711048, 2.970321, 3.217389, 2.932718, 2.624368, 2.352993, 2.082754, 1.810212, 1.646672, 1.569297, 1.491467, 1.423370, 1.492610, 1.582647, 1.678358, 1.716821, 1.731670, 1.742986, 1.767574, 1.788344, 1.848281, 1.924665, 1.988000, 2.031830, 2.053062, 2.051388, 2.028654, 2.015005, 1.997957, 1.965142, 1.917382, 1.857503, 1.788385, 1.771228, 1.796693, 1.870228, 1.943581, 2.021260, 2.054921, 1.980951, 1.904006, 1.828339, 1.755348, 1.684527, 1.679269, 1.671303, 1.649506, 1.625713, 1.628626, 1.648026, 1.666853, 1.666971, 1.667175, 1.667467, 1.666864, 1.667084, 1.684850, 1.746219, 1.837623, 1.910701, 1.957042, 2.000533, 2.013282, 1.988075, 1.928301, 1.840060, 1.778288, 1.751141, 1.715243, 1.674226, 1.667609, 1.668506, 1.813702, 2.104265, 2.393819, 2.684587, 3.046101, 3.384994, 3.192045, 2.972115, 2.759418, 2.482197, 2.116255, 1.872193, 1.719995, 1.569437, 1.428284, 1.478678, 1.535603, 1.593704, 1.612562, 1.631727, 1.650695, 1.667505, 1.666869, 1.714170, 1.788377, 1.857396, 1.918347, 1.984665, 2.035935, 2.069546, 2.083054, 2.077945, 2.072123, 2.043477, 1.993317, 1.924058, 1.840950, 1.774595, 1.746742, 1.709275, 1.670271, 1.667451, 1.666985, 1.667148, 1.667431, 1.666855, 1.674092, 1.768188, 1.852103, 1.921662, 1.981773, 2.042887, 2.034716, 1.959394, 1.881436, 1.807542, 1.734764, 1.666921, 1.650721, 1.632011, 1.612468, 1.615475, 1.635118, 1.696940, 1.785410, 1.844420, 1.879900, 1.896901, 1.889448, 1.856167, 1.814899, 1.751595, 1.723699, 1.737245, 1.751599, 1.776796, 1.795679, 1.804909, 1.948744, 2.229112, 2.497925, 2.777122, 3.037542, 3.264089, 2.979059, 2.684412, 2.393800, 2.104719, 1.813015, 1.646899, 1.616041, 1.611036, 1.615455, 1.758055, 1.911681, 2.044704, 1.991481, 1.915183, 1.838675, 1.765942, 1.693187, 1.705778, 1.761068, 1.811126, 1.857551, 1.924147, 1.976000, 2.012351, 2.015029, 1.997969, 1.965109, 1.917429, 1.857469, 1.802899, 1.746856, 1.716804, 1.732051, 1.742765, 1.767328, 1.788879, 1.801121, 1.803435, 1.797990, 1.781155, 1.757224, 1.743682, 1.714843, 1.698382, 1.682678, 1.666817, 1.667198, 1.667438, 1.666792, 1.724473, 1.797583, 1.913446, 2.062087, 2.198545, 2.267387, 2.210872, 2.126288, 2.013896, 1.880863, 1.726571, 1.656268, 1.637341, 1.618436, 1.609605, 1.629443, 1.649288, 1.813186, 2.104266, 2.393792, 2.684767, 2.978710, 3.244554, 2.996051, 2.719860, 2.445964, 2.170994, 1.890216, 1.750280, 1.693147, 1.626587, 1.559206, 1.621497, 1.679338, 1.729157, 1.714856, 1.674837, 1.667162, 1.667388, 1.666873, 1.714177, 1.788381, 1.857259, 1.985007, 2.105253, 2.210977, 2.302897, 2.380644, 2.375454, 2.268314, 2.142938, 2.008160, 1.866497, 1.718879, 1.653010, 1.634876, 1.615205, 1.612744, 1.631970, 1.650522, 1.667275, 1.667392, 1.666944, 1.669119, 1.690214, 1.702135, 1.718114, 1.720667, 1.721141, 1.735258, 1.747373, 1.773948, 1.793258, 1.803317, 1.802369, 1.794225, 1.775485, 1.789960, 1.827781, 1.847556, 1.879917, 1.896906, 1.889430, 1.852988, 1.792370, 1.716136, 1.745187, 1.818141, 1.890592, 1.965665, 2.043704, 2.035978, 2.105727, 2.318731, 2.534124, 2.752129, 2.978671, 3.228221, 2.943575, 2.630037, 2.342050, 2.072005, 1.799224, 1.646620, 1.569298, 1.491491, 1.423334, 1.492619, 1.571487, 1.667901, 1.707381, 1.723641, 1.736983, 1.751640, 1.777115, 1.842219, 1.924791, 1.992448, 2.042473, 2.069067, 2.090318, 2.074047, 2.034420, 1.998002, 1.965114, 1.917414, 1.857469, 1.788492, 1.714180, 1.713679, 1.787117, 1.859053, 1.932586, 2.010816, 2.066253, 1.991679, 1.915414, 1.838436, 1.767684, 1.716538, 1.693015, 1.671097, 1.636382, 1.606745, 1.626423, 1.645439, 1.664096, 1.667233, 1.667397, 1.666934, 1.667210, 1.667469, 1.681210, 1.700131, 1.717694, 1.775269, 1.861271, 1.944613, 2.001345, 2.031042, 2.025717, 1.983547, 1.906670, 1.799268, 1.720681, 1.680881, 1.667312, 1.666570, 1.667634, 1.668460, 1.813771, 2.104087, 2.451090, 2.814520, 3.181364, 3.521323, 3.333152, 3.071918, 2.707750, 2.341568, 1.973991, 1.735082, 1.584702, 1.480510, 1.393260, 1.443700, 1.511324, 1.607274, 1.647742, 1.666649, 1.667191, 1.667354, 1.666906, 1.714167, 1.788233, 1.874459, 1.953043, 2.017118, 2.064240, 2.092355, 2.118637, 2.121819, 2.100229, 2.053265, 1.986356, 1.899590, 1.797655, 1.714839, 1.675218, 1.666941, 1.667142, 1.667408, 1.667017, 1.667151, 1.667249, 1.734332, 1.809415, 1.903271, 1.989086, 2.064021, 2.060175, 1.969989, 1.892754, 1.818095, 1.744904, 1.672124, 1.653900, 1.678094, 1.733711, 1.790029, 1.844436, 1.880443, 1.889557, 1.852949, 1.792459, 1.711163, 1.667213, 1.668173, 1.687044, 1.704559, 1.721958, 1.736520, 1.893706, 2.211243, 2.519875, 2.820617, 3.114215, 3.371695, 3.086686, 2.763601, 2.436122, 2.107849, 1.813091, 1.646653, 1.569295, 1.491500, 1.423275, 1.497573, 1.646237, 1.796157, 1.890205, 1.966276, 2.043257, 2.034860, 1.958898, 1.928643, 1.928686, 1.924801, 1.917577, 1.948480, 1.962507, 1.960819, 1.963276, 1.965262, 1.951283, 1.917365, 1.857463, 1.788551, 1.713929, 1.666951, 1.670760, 1.689402, 1.707563, 1.723884, 1.736810, 1.751410, 1.777638, 1.794993, 1.805533, 1.825266, 1.826261, 1.802516, 1.770792, 1.720240, 1.680675, 1.667370, 1.666948, 1.667208, 1.667424, 1.666905, 1.667063, 1.725097, 1.840550, 1.988119, 2.121041, 2.234028, 2.284413, 2.203376, 2.089837, 1.953371, 1.799573, 1.682656, 1.656459, 1.637238, 1.617679, 1.610650, 1.630314, 1.794520, 2.103711, 2.393829, 2.684736, 2.978715, 3.244701, 2.978879, 2.701458, 2.429233, 2.156870, 1.879358, 1.723815, 1.672902, 1.615343, 1.558455, 1.628448, 1.697195, 1.756495, 1.750651, 1.715097, 1.674824, 1.667362, 1.666909, 1.714139, 1.788390, 1.857435, 1.917441, 2.032351, 2.138251, 2.228234, 2.302714, 2.363581, 2.342620, 2.220599, 2.082980, 1.939251, 1.791900, 1.671858, 1.653649, 1.634261, 1.615380, 1.613046, 1.631541, 1.650665, 1.667549, 1.666909, 1.669125, 1.690249, 1.701366, 1.699140, 1.702395, 1.704787, 1.721567, 1.735419, 1.746936, 1.774224, 1.793454, 1.802815, 1.802706, 1.794456, 1.774524, 1.746399, 1.710141, 1.713929, 1.785480, 1.844397, 1.879900, 1.896935, 1.889346, 1.857951, 1.870392, 1.861865, 1.890504, 1.966525, 2.043416, 2.034062, 1.959624, 1.882974, 1.954036, 2.171622, 2.393779, 2.668282, 2.943259, 3.190305, 2.927270, 2.651726, 2.379954, 2.104657, 1.813077, 1.646673, 1.569270, 1.491509, 1.426547, 1.515046, 1.608730, 1.701906, 1.736755, 1.751453, 1.777394, 1.795432, 1.803312, 1.849181, 1.913237, 1.961541, 2.009976, 2.023792, 2.017346, 2.015248, 2.015011, 1.997963, 1.965142, 1.917359, 1.857686, 1.835297, 1.833499, 1.859029, 1.933026, 2.010339, 2.066380, 1.991965, 1.914997, 1.838645, 1.765986, 1.693227, 1.660733, 1.662940, 1.655096, 1.638344, 1.641877, 1.644852, 1.664372, 1.667394, 1.666960, 1.667175, 1.667834, 1.710799, 1.799811, 1.877319, 1.929771, 1.961405, 1.965238, 1.953175, 1.913899, 1.845280, 1.803536, 1.798008, 1.781553, 1.754684, 1.721057, 1.681937, 1.813703, 2.104243, 2.393825, 2.684724, 2.978751, 3.244533, 3.036298, 2.814191, 2.596460, 2.381334, 2.167202, 2.034160, 1.883241, 1.728349, 1.584276, 1.581010, 1.583706, 1.634475, 1.636851, 1.618290, 1.610203, 1.629196, 1.647691, 1.713623, 1.788391, 1.857408, 1.917621, 1.964960, 1.997811, 2.032240, 2.050459, 2.050127, 2.031417, 1.994560, 1.961067, 1.912403, 1.849050, 1.802780, 1.796468, 1.777996, 1.750833, 1.715340, 1.674651, 1.667132, 1.667432, 1.666913, 1.669132, 1.690253, 1.701229, 1.765800, 1.822983, 1.879829, 1.954918, 2.033009, 2.044445, 1.970380, 1.892980, 1.817562, 1.745175, 1.672748, 1.696867, 1.752767, 1.792665, 1.825512, 1.861462, 1.872947, 1.853106, 1.792424, 1.711169, 1.667248, 1.667404, 1.668070, 1.686287, 1.705604, 1.722829, 1.881752, 2.184231, 2.500806, 2.810819, 3.114565, 3.380206, 3.106020, 2.792093, 2.472974, 2.147028, 1.816213, 1.646690, 1.569275, 1.491491, 1.423368, 1.492517, 1.573271, 1.723394, 1.817651, 1.890446, 1.966263, 2.043457, 2.034381, 2.006164, 2.002856, 1.997740, 1.984983, 1.964921, 1.981478, 1.979764, 1.960636, 1.946213, 1.932428, 1.903568};
				double[] toracica={2.385201, 2.090608, 1.799981, 1.510887, 1.219251, 1.052850, 0.975472, 0.897668, 0.829536, 0.898778, 0.974476, 1.051633, 1.073127, 1.073368, 1.073355, 1.073555, 1.073076, 1.120342, 1.194555, 1.263609, 1.323791, 1.371135, 1.404133, 1.421390, 1.421207, 1.404144, 1.371310, 1.323595, 1.263637, 1.194728, 1.120131, 1.073110, 1.073749, 1.073134, 1.073309, 1.073611, 1.073182, 1.073325, 1.073599, 1.073116, 1.075297, 1.096427, 1.107579, 1.104584, 1.088865, 1.072985, 1.073411, 1.073572, 1.073135, 1.073376, 1.073637, 1.073039, 1.073406, 1.074000, 1.116967, 1.191640, 1.250594, 1.286077, 1.303103, 1.295607, 1.259156, 1.198631, 1.117341, 1.073426, 1.073617, 1.073514, 1.072757, 1.073802, 1.074673, 1.219905, 1.510430, 1.799993, 2.090937, 2.384885, 2.650876, 2.385201, 2.090608, 1.799981, 1.510887, 1.219251, 1.052850, 0.975472, 0.897668, 0.829536, 0.898778, 0.974476, 1.051633, 1.073127, 1.073368, 1.073355, 1.073555, 1.073076, 1.120342, 1.194555, 1.263609, 1.323791, 1.371135, 1.404133, 1.421390, 1.421207, 1.404144, 1.371310, 1.323595, 1.263637, 1.194728, 1.120131, 1.073110, 1.073749, 1.073134, 1.073309, 1.073611, 1.073182, 1.073325, 1.073599, 1.073116, 1.075297, 1.096427, 1.107579, 1.104584, 1.088865, 1.072985, 1.073411, 1.073572, 1.073135, 1.073376, 1.073637, 1.073039, 1.073406, 1.074000, 1.116967, 1.191640, 1.250594, 1.286077, 1.303103, 1.295607, 1.259156, 1.198631, 1.117341, 1.073426, 1.073617, 1.073514, 1.072757, 1.073802, 1.074673, 1.219905, 1.510430, 1.799993, 2.090937, 2.384885, 2.650876, 2.385201, 2.090608, 1.799981, 1.510887, 1.219251, 1.052850, 0.975472, 0.897668, 0.829536, 0.898778, 0.974476, 1.051633, 1.073127, 1.073368, 1.073355, 1.073555, 1.073076, 1.120342, 1.194555, 1.263609, 1.323791, 1.371135, 1.404133, 1.421390, 1.421207, 1.404144, 1.371310, 1.323595, 1.263637, 1.194728, 1.120131, 1.073110, 1.073749, 1.073134, 1.073309, 1.073611, 1.073182, 1.073325, 1.073599, 1.073116, 1.075297, 1.096427, 1.107579, 1.104584, 1.088865, 1.072985, 1.073411, 1.073572, 1.073135, 1.073376, 1.074000, 1.116967, 1.191640, 1.250594, 1.286077, 1.303103, 1.295607, 1.259156, 1.198631, 1.117341, 1.073426, 1.073617, 1.073514, 1.072757, 1.073802, 1.074673, 1.219905, 1.510430, 1.799993, 2.090937, 2.384885, 2.650876, 2.385201, 2.090608, 1.799981, 1.510887, 1.219251, 1.052850, 0.975472, 0.897668, 0.829536, 0.898778, 0.974476, 1.051633, 1.073127, 1.073368, 1.073355, 1.073555, 1.073076, 1.120342, 1.194555, 1.263609, 1.323791, 1.371135, 1.404133, 1.421390, 1.421207, 1.404144, 1.371310, 1.323595, 1.263637, 1.194728, 1.120131, 1.073110, 1.073749, 1.073134, 1.073309, 1.073611, 1.073182, 1.073325, 1.073599, 1.073116, 1.075297, 1.096427, 1.107579, 1.104584, 1.088865, 1.072985, 1.073411, 1.073572, 1.073135, 1.073376, 1.073637, 1.073039, 1.073406, 1.074000, 1.116967, 1.191640, 1.250594, 1.286077, 1.303103, 1.295607, 1.259156, 1.198631, 1.117341, 1.073426, 1.073617, 1.073514, 1.072757, 1.073802, 1.074673, 1.219905, 1.510430, 1.799993, 2.090937, 2.384885, 2.650876, 2.385201, 2.090608, 1.799981, 1.510887, 1.219251, 1.052850, 0.975472, 0.897668, 0.829536, 0.898778, 0.974476, 1.051633, 1.073127, 1.073368, 1.073355, 1.073555, 1.073076, 1.120342, 1.194555, 1.263609, 1.323791, 1.371135, 1.404133, 1.421390, 1.421207, 1.404144, 1.371310, 1.323595, 1.263637, 1.194728, 1.120131, 1.073110, 1.073749, 1.073134, 1.073309, 1.073611, 1.073182, 1.073325, 1.073599, 1.073116, 1.075297, 1.096427, 1.107579, 1.104584, 1.088865, 1.072985, 1.073411, 1.073572, 1.073135, 1.073411, 1.073572, 1.073135, 1.073376, 1.073637, 1.073039, 1.073406, 1.074000, 1.116967, 1.191640, 1.250594, 1.286077, 1.303103, 1.295607, 1.259156, 1.198631, 1.117341, 1.073426, 1.073617, 1.073514, 1.072757, 1.073802, 1.074673, 1.219905, 1.510430, 1.799993, 2.090937, 2.384885, 2.650876, 2.385201, 2.090608, 1.799981, 1.510887, 1.219251, 1.052850, 0.975472, 0.897668, 0.829536, 0.898778, 0.974476, 1.051633, 1.073127, 1.073368, 1.073355, 1.073555, 1.073076, 1.120342, 1.194555, 1.263609, 1.323791, 1.371135, 1.404133, 1.421390, 1.421207, 1.404144, 1.371310, 1.323595, 1.263637, 1.194728, 1.120131, 1.073110, 1.073749, 1.073134, 1.073309, 1.073611, 1.073182, 1.073325, 1.073599, 1.073116, 1.075297, 1.096427, 1.107579, 1.104584, 1.088865, 1.072985, 1.073411, 1.073572, 1.073135, 1.073376, 1.074000, 1.116967, 1.191640, 1.250594, 1.286077, 1.303103, 1.295607, 1.259156, 1.198631, 1.117341, 1.073426, 1.073617, 1.073514, 1.072757, 1.073802, 1.074673, 1.219905, 1.510430, 1.799993, 2.090937, 2.384885, 2.650876, 2.385201, 2.090608, 1.799981, 1.510887, 1.219251, 1.052850, 0.975472, 0.897668, 0.829536, 0.898778, 0.974476, 1.051633, 1.073127, 1.073368, 1.073355, 1.073555, 1.073076, 1.120342, 1.194555, 1.263609, 1.323791, 1.371135, 1.404133, 1.421390, 1.421207, 1.404144, 1.371310, 1.323595, 1.263637, 1.194728, 1.120131, 1.073110, 1.073749, 1.073134, 1.073309, 1.073611, 1.073182, 1.073325, 1.073599, 1.073116, 1.075297, 1.096427, 1.107579, 1.104584, 1.088865, 1.072985, 1.073411, 1.073572, 1.073135, 1.073376, 1.073637, 1.073039, 1.073406, 1.074000, 1.116967, 1.191640, 1.250594, 1.286077, 1.303103, 1.295607, 1.259156, 1.198631, 1.117341, 1.073426, 1.073617, 1.073514, 1.072757, 1.073802, 1.074673, 1.219905, 1.510430, 1.799993, 2.090937, 2.384885, 2.650876, 2.385201, 2.090608, 1.799981, 1.510887, 1.219251, 1.052850, 0.975472, 0.897668, 0.829536, 0.898778, 0.974476, 1.051633, 1.073127, 1.073368, 1.073355, 1.073555, 1.073076, 1.120342, 1.194555, 1.263609, 1.323791, 1.371135, 1.404133, 1.421390, 1.421207, 1.404144, 1.371310, 1.323595, 1.263637, 1.194728, 1.120131, 1.073110, 1.073749, 1.073134, 1.073309, 1.073611, 1.073182, 1.073325, 1.073599, 1.073116, 1.075297, 1.096427, 1.107579, 1.104584, 1.088865, 1.072985, 1.073411, 1.073572, 1.073135, 1.073411, 1.073572, 1.073135, 1.073376, 1.073637, 1.073039, 1.073406, 1.074000, 1.116967, 1.191640, 1.250594, 1.286077, 1.303103, 1.295607, 1.259156, 1.198631, 1.117341, 1.073426, 1.073617, 1.073514, 1.072757, 1.073802, 1.074673, 1.219905, 1.510430, 1.799993, 2.090937, 2.384885, 2.650876, 2.385201, 2.090608, 1.799981, 1.510887, 1.219251, 1.052850, 0.975472, 0.897668, 0.829536, 0.898778, 0.974476, 1.051633, 1.073127, 1.073368, 1.073355, 1.073555, 1.073076, 1.120342, 1.194555, 1.263609, 1.323791, 1.371135, 1.404133, 1.421390, 1.421207, 1.404144, 1.371310, 1.323595, 1.263637, 1.194728, 1.120131, 1.073110, 1.073749, 1.073134, 1.073309, 1.073611, 1.073182, 1.073325, 1.073599, 1.073116, 1.075297, 1.096427, 1.107579, 1.104584, 1.088865, 1.072985, 1.073411, 1.073572, 1.073135, 1.073376, 1.074000, 1.116967, 1.191640, 1.250594, 1.286077, 1.303103, 1.295607, 1.259156, 1.198631, 1.117341, 1.073426, 1.073617, 1.073514, 1.072757, 1.073802, 1.074673, 1.219905, 1.510430, 1.799993, 2.090937, 2.384885, 2.650876, 2.385201, 2.090608, 1.799981, 1.510887, 1.219251, 1.052850, 0.975472, 0.897668, 0.829536, 0.898778, 0.974476, 1.051633, 1.073127, 1.073368, 1.073355, 1.073555, 1.073076, 1.120342, 1.194555, 1.263609, 1.323791, 1.371135, 1.404133, 1.421390, 1.421207, 1.404144, 1.371310, 1.323595, 1.263637, 1.194728, 1.120131, 1.073110, 1.073749, 1.073134, 1.073309, 1.073611, 1.073182, 1.073325, 1.073599, 1.073116, 1.075297, 1.096427, 1.107579, 1.104584, 1.088865, 1.072985, 1.073411, 1.073572, 1.073135, 1.073376, 1.073637, 1.073039, 1.073406, 1.074000, 1.116967, 1.191640, 1.250594, 1.286077, 1.303103, 1.295607, 1.259156, 1.198631, 1.117341, 1.073426, 1.073617, 1.073514, 1.072757, 1.073802, 1.074673, 1.219905, 1.510430, 1.799993, 2.090937, 2.384885, 2.650876, 2.385201, 2.090608, 1.799981, 1.510887, 1.219251, 1.052850, 0.975472, 0.897668, 0.829536, 0.898778, 0.974476, 1.051633, 1.073127, 1.073368, 1.073355, 1.073555, 1.073076, 1.120342, 1.194555, 1.263609, 1.323791, 1.371135, 1.404133, 1.421390, 1.421207, 1.404144, 1.371310, 1.323595, 1.263637, 1.194728, 1.120131, 1.073110, 1.073749, 1.073134, 1.073309, 1.073611, 1.073182, 1.073325, 1.073599, 1.073116, 1.075297, 1.096427, 1.107579, 1.104584, 1.088865, 1.072985, 1.073411, 1.073572, 1.073135, 1.073411, 1.073572, 1.073135, 1.073376, 1.073637, 1.073039, 1.073406, 1.074000, 1.116967, 1.191640, 1.250594, 1.286077, 1.303103, 1.295607, 1.259156, 1.198631, 1.117341, 1.073426, 1.073617, 1.073514, 1.072757, 1.073802, 1.074673, 1.219905, 1.510430, 1.799993, 2.090937, 2.384885, 2.650876, 2.385201, 2.090608, 1.799981, 1.510887, 1.219251, 1.052850, 0.975472, 0.897668, 0.829536, 0.898778, 0.974476, 1.051633, 1.073127, 1.073368, 1.073355, 1.073555, 1.073076, 1.120342, 1.194555, 1.263609, 1.323791, 1.371135, 1.404133, 1.421390, 1.421207, 1.404144, 1.371310, 1.323595, 1.263637, 1.194728, 1.120131, 1.073110, 1.073749, 1.073134, 1.073309, 1.073611, 1.073182, 1.073325, 1.073599, 1.073116, 1.075297, 1.096427, 1.107579, 1.104584, 1.088865, 1.072985, 1.073411, 1.073572, 1.073135, 1.073376, 1.074000, 1.116967, 1.191640, 1.250594, 1.286077, 1.303103, 1.295607, 1.259156, 1.198631, 1.117341, 1.073426, 1.073617, 1.073514, 1.072757, 1.073802, 1.074673, 1.219905, 1.510430, 1.799993, 2.090937, 2.384885, 2.650876, 2.385201, 2.090608, 1.799981, 1.510887, 1.219251, 1.052850, 0.975472, 0.897668, 0.829536, 0.898778, 0.974476, 1.051633, 1.073127, 1.073368, 1.073355, 1.073555, 1.073076, 1.120342, 1.194555, 1.263609, 1.323791, 1.371135, 1.404133, 1.421390, 1.421207, 1.404144, 1.371310, 1.323595, 1.263637, 1.194728, 1.120131, 1.073110, 1.073749, 1.073134, 1.073309, 1.073611, 1.073182, 1.073325, 1.073599, 1.073116, 1.075297, 1.096427, 1.107579, 1.104584, 1.088865, 1.072985, 1.073411, 1.073572, 1.073135, 1.073376, 1.073637, 1.073039, 1.073406, 1.074000, 1.116967, 1.191640, 1.250594, 1.286077, 1.303103, 1.295607, 1.259156, 1.198631, 1.117341, 1.073426, 1.073617, 1.073514, 1.072757, 1.073802, 1.074673, 1.219905, 1.510430, 1.799993, 2.090937, 2.384885, 2.650876, 2.385201, 2.090608, 1.799981, 1.510887, 1.219251, 1.052850, 0.975472, 0.897668, 0.829536, 0.898778, 0.974476, 1.051633, 1.073127, 1.073368, 1.073355, 1.073555, 1.073076, 1.120342, 1.194555, 1.263609, 1.323791, 1.371135, 1.404133, 1.421390, 1.421207, 1.404144, 1.371310, 1.323595};
				double fetal;
				ANC separa = new ANC();
				for(int i=0; i<M; i++){
					fetal = separa.ajustar(toracica[i], abdominal[i],i);
				}*/
				
			}
		});
        detener.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View arg0) {
				stopService(new Intent(getApplicationContext(), Servicio.class));
				
				
			}
		});
        //Cuando se da click a un dispositivo encontrado se inicia la conexión con este
        listaBt.setOnItemClickListener(new OnItemClickListener()
        {
        	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3 )
        	{
        		String item = ((TextView)arg1).getText().toString();
        		String items[] = item.split(" ");
        		for (BluetoothDevice dispositivo: dispositivos){
        			if(items[1].equals(dispositivo.getAddress())){
        				//AQUÍ TIENEN QUE INICIAR EL SERVICIO 
        				//Pasar los datos necesarios en el intent
        				Intent intent = new Intent(getApplicationContext(), Servicio.class);
        				intent.putExtra("dispositivo", dispositivo);
        				startService(intent);
        				//ClienteBT conectar = new ClienteBT(dispositivo, MainActivity.this);
        				//conectar.execute();
        			}	
    			}	
        	}
        });  
        
       
        
    }
    
    public void addRowToList(String row){
    	bt.getEncontrados().add(row);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
