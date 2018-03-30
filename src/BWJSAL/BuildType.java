package BWJSAL;

import bwapi.Race;
import bwapi.TechType;
import bwapi.UnitType;
import bwapi.UpgradeType;

public enum BuildType {
    Terran_Marine(0 ,bwapi.UnitType.Terran_Marine),
    Terran_Ghost(1 ,bwapi.UnitType.Terran_Ghost),
    Terran_Vulture(2 ,bwapi.UnitType.Terran_Vulture),
    Terran_Goliath(3 ,bwapi.UnitType.Terran_Goliath),
    Terran_Siege_Tank_Tank_Mode(4 ,bwapi.UnitType.Terran_Siege_Tank_Tank_Mode),
    Terran_SCV(5 ,bwapi.UnitType.Terran_SCV),
    Terran_Wraith(6 ,bwapi.UnitType.Terran_Wraith),
    Terran_Science_Vessel(7 ,bwapi.UnitType.Terran_Science_Vessel),
    Terran_Dropship(8 ,bwapi.UnitType.Terran_Dropship),
    Terran_Battlecruiser(9 ,bwapi.UnitType.Terran_Battlecruiser),
    Terran_Nuclear_Missile(10 ,bwapi.UnitType.Terran_Nuclear_Missile),
    Terran_Siege_Tank_Siege_Mode(11 ,bwapi.UnitType.Terran_Siege_Tank_Siege_Mode),
    Terran_Firebat(12 ,bwapi.UnitType.Terran_Firebat),
    Terran_Medic(13 ,bwapi.UnitType.Terran_Medic),
    Zerg_Larva(14 ,bwapi.UnitType.Zerg_Larva),
    Zerg_Zergling(15 ,bwapi.UnitType.Zerg_Zergling),
    Zerg_Hydralisk(16 ,bwapi.UnitType.Zerg_Hydralisk),
    Zerg_Ultralisk(17 ,bwapi.UnitType.Zerg_Ultralisk),
    Zerg_Drone(18 ,bwapi.UnitType.Zerg_Drone),
    Zerg_Overlord(19 ,bwapi.UnitType.Zerg_Overlord),
    Zerg_Mutalisk(20 ,bwapi.UnitType.Zerg_Mutalisk),
    Zerg_Guardian(21 ,bwapi.UnitType.Zerg_Guardian),
    Zerg_Queen(22 ,bwapi.UnitType.Zerg_Queen),
    Zerg_Defiler(23 ,bwapi.UnitType.Zerg_Defiler),
    Zerg_Scourge(24 ,bwapi.UnitType.Zerg_Scourge),
    Zerg_Infested_Terran(25 ,bwapi.UnitType.Zerg_Infested_Terran),
    Terran_Valkyrie(26 ,bwapi.UnitType.Terran_Valkyrie),
    Zerg_Cocoon(27 ,bwapi.UnitType.Zerg_Cocoon),
    Protoss_Corsair(28 ,bwapi.UnitType.Protoss_Corsair),
    Protoss_Dark_Templar(29 ,bwapi.UnitType.Protoss_Dark_Templar),
    Zerg_Devourer(30 ,bwapi.UnitType.Zerg_Devourer),
    Protoss_Dark_Archon(31 ,bwapi.UnitType.Protoss_Dark_Archon),
    Protoss_Probe(32 ,bwapi.UnitType.Protoss_Probe),
    Protoss_Zealot(33 ,bwapi.UnitType.Protoss_Zealot),
    Protoss_Dragoon(34 ,bwapi.UnitType.Protoss_Dragoon),
    Protoss_High_Templar(35 ,bwapi.UnitType.Protoss_High_Templar),
    Protoss_Archon(36 ,bwapi.UnitType.Protoss_Archon),
    Protoss_Shuttle(37 ,bwapi.UnitType.Protoss_Shuttle),
    Protoss_Scout(38 ,bwapi.UnitType.Protoss_Scout),
    Protoss_Arbiter(39 ,bwapi.UnitType.Protoss_Arbiter),
    Protoss_Carrier(40 ,bwapi.UnitType.Protoss_Carrier),
    Protoss_Interceptor(41 ,bwapi.UnitType.Protoss_Interceptor),
    Protoss_Reaver(42 ,bwapi.UnitType.Protoss_Reaver),
    Protoss_Observer(43 ,bwapi.UnitType.Protoss_Observer),
    Protoss_Scarab(44 ,bwapi.UnitType.Protoss_Scarab),
    Zerg_Lurker(45 ,bwapi.UnitType.Zerg_Lurker),
    Terran_Command_Center(46 ,bwapi.UnitType.Terran_Command_Center),
    Terran_Comsat_Station(47 ,bwapi.UnitType.Terran_Comsat_Station),
    Terran_Nuclear_Silo(48 ,bwapi.UnitType.Terran_Nuclear_Silo),
    Terran_Supply_Depot(49 ,bwapi.UnitType.Terran_Supply_Depot),
    Terran_Refinery(50 ,bwapi.UnitType.Terran_Refinery),
    Terran_Barracks(51 ,bwapi.UnitType.Terran_Barracks),
    Terran_Academy(52 ,bwapi.UnitType.Terran_Academy),
    Terran_Factory(53 ,bwapi.UnitType.Terran_Factory),
    Terran_Starport(54 ,bwapi.UnitType.Terran_Starport),
    Terran_Control_Tower(55 ,bwapi.UnitType.Terran_Control_Tower),
    Terran_Science_Facility(56 ,bwapi.UnitType.Terran_Science_Facility),
    Terran_Covert_Ops(57 ,bwapi.UnitType.Terran_Covert_Ops),
    Terran_Physics_Lab(58 ,bwapi.UnitType.Terran_Physics_Lab),
    Terran_Machine_Shop(59 ,bwapi.UnitType.Terran_Machine_Shop),
    Terran_Engineering_Bay(60 ,bwapi.UnitType.Terran_Engineering_Bay),
    Terran_Armory(61 ,bwapi.UnitType.Terran_Armory),
    Terran_Missile_Turret(62 ,bwapi.UnitType.Terran_Missile_Turret),
    Terran_Bunker(63 ,bwapi.UnitType.Terran_Bunker),
    Zerg_Hatchery(64 ,bwapi.UnitType.Zerg_Hatchery),
    Zerg_Lair(65 ,bwapi.UnitType.Zerg_Lair),
    Zerg_Hive(66 ,bwapi.UnitType.Zerg_Hive),
    Zerg_Nydus_Canal(67 ,bwapi.UnitType.Zerg_Nydus_Canal),
    Zerg_Hydralisk_Den(68 ,bwapi.UnitType.Zerg_Hydralisk_Den),
    Zerg_Defiler_Mound(69 ,bwapi.UnitType.Zerg_Defiler_Mound),
    Zerg_Greater_Spire(70 ,bwapi.UnitType.Zerg_Greater_Spire),
    Zerg_Queens_Nest(71 ,bwapi.UnitType.Zerg_Queens_Nest),
    Zerg_Evolution_Chamber(72 ,bwapi.UnitType.Zerg_Evolution_Chamber),
    Zerg_Ultralisk_Cavern(73 ,bwapi.UnitType.Zerg_Ultralisk_Cavern),
    Zerg_Spire(74 ,bwapi.UnitType.Zerg_Spire),
    Zerg_Spawning_Pool(75 ,bwapi.UnitType.Zerg_Spawning_Pool),
    Zerg_Creep_Colony(76 ,bwapi.UnitType.Zerg_Creep_Colony),
    Zerg_Spore_Colony(77 ,bwapi.UnitType.Zerg_Spore_Colony),
    Zerg_Sunken_Colony(78 ,bwapi.UnitType.Zerg_Sunken_Colony),
    Zerg_Extractor(79 ,bwapi.UnitType.Zerg_Extractor),
    Protoss_Nexus(80 ,bwapi.UnitType.Protoss_Nexus),
    Protoss_Robotics_Facility(81 ,bwapi.UnitType.Protoss_Robotics_Facility),
    Protoss_Pylon(82 ,bwapi.UnitType.Protoss_Pylon),
    Protoss_Assimilator(83 ,bwapi.UnitType.Protoss_Assimilator),
    Protoss_Observatory(84 ,bwapi.UnitType.Protoss_Observatory),
    Protoss_Gateway(85 ,bwapi.UnitType.Protoss_Gateway),
    Protoss_Photon_Cannon(86 ,bwapi.UnitType.Protoss_Photon_Cannon),
    Protoss_Citadel_of_Adun(87 ,bwapi.UnitType.Protoss_Citadel_of_Adun),
    Protoss_Cybernetics_Core(88 ,bwapi.UnitType.Protoss_Cybernetics_Core),
    Protoss_Templar_Archives(89 ,bwapi.UnitType.Protoss_Templar_Archives),
    Protoss_Forge(90 ,bwapi.UnitType.Protoss_Forge),
    Protoss_Stargate(91 ,bwapi.UnitType.Protoss_Stargate),
    Protoss_Fleet_Beacon(92 ,bwapi.UnitType.Protoss_Fleet_Beacon),
    Protoss_Arbiter_Tribunal(93 ,bwapi.UnitType.Protoss_Arbiter_Tribunal),
    Protoss_Robotics_Support_Bay(94 ,bwapi.UnitType.Protoss_Robotics_Support_Bay),
    Protoss_Shield_Battery(95 ,bwapi.UnitType.Protoss_Shield_Battery),
    Stim_Packs(96 ,bwapi.TechType.Stim_Packs),
    Lockdown(97 ,bwapi.TechType.Lockdown),
    EMP_Shockwave(98 ,bwapi.TechType.EMP_Shockwave),
    Spider_Mines(99 ,bwapi.TechType.Spider_Mines),
    Tank_Siege_Mode(100 ,bwapi.TechType.Tank_Siege_Mode),
    Irradiate(101 ,bwapi.TechType.Irradiate),
    Yamato_Gun(102 ,bwapi.TechType.Yamato_Gun),
    Cloaking_Field(103 ,bwapi.TechType.Cloaking_Field),
    Personnel_Cloaking(104 ,bwapi.TechType.Personnel_Cloaking),
    Burrowing(105 ,bwapi.TechType.Burrowing),
    Spawn_Broodlings(106 ,bwapi.TechType.Spawn_Broodlings),
    Plague(107 ,bwapi.TechType.Plague),
    Consume(108 ,bwapi.TechType.Consume),
    Ensnare(109 ,bwapi.TechType.Ensnare),
    Psionic_Storm(110 ,bwapi.TechType.Psionic_Storm),
    Hallucination(111 ,bwapi.TechType.Hallucination),
    Recall(112 ,bwapi.TechType.Recall),
    Stasis_Field(113 ,bwapi.TechType.Stasis_Field),
    Restoration(114 ,bwapi.TechType.Restoration),
    Disruption_Web(115 ,bwapi.TechType.Disruption_Web),
    Mind_Control(116 ,bwapi.TechType.Mind_Control),
    Optical_Flare(117 ,bwapi.TechType.Optical_Flare),
    Maelstrom(118 ,bwapi.TechType.Maelstrom),
    Lurker_Aspect(119 ,bwapi.TechType.Lurker_Aspect),
    Terran_Infantry_Armor_1(120 ,bwapi.UpgradeType.Terran_Infantry_Armor, 1),
    Terran_Infantry_Armor_2(121 ,bwapi.UpgradeType.Terran_Infantry_Armor, 2),
    Terran_Infantry_Armor_3(122 ,bwapi.UpgradeType.Terran_Infantry_Armor, 3),
    Terran_Vehicle_Plating_1(123 ,bwapi.UpgradeType.Terran_Vehicle_Plating, 1),
    Terran_Vehicle_Plating_2(124 ,bwapi.UpgradeType.Terran_Vehicle_Plating, 2),
    Terran_Vehicle_Plating_3(125 ,bwapi.UpgradeType.Terran_Vehicle_Plating, 3),
    Terran_Ship_Plating_1(126 ,bwapi.UpgradeType.Terran_Ship_Plating, 1),
    Terran_Ship_Plating_2(127 ,bwapi.UpgradeType.Terran_Ship_Plating, 2),
    Terran_Ship_Plating_3(128 ,bwapi.UpgradeType.Terran_Ship_Plating, 3),
    Zerg_Carapace_1(129 ,bwapi.UpgradeType.Zerg_Carapace, 1),
    Zerg_Carapace_2(130 ,bwapi.UpgradeType.Zerg_Carapace, 2),
    Zerg_Carapace_3(131 ,bwapi.UpgradeType.Zerg_Carapace, 3),
    Zerg_Flyer_Carapace_1(132 ,bwapi.UpgradeType.Zerg_Flyer_Carapace, 1),
    Zerg_Flyer_Carapace_2(133 ,bwapi.UpgradeType.Zerg_Flyer_Carapace, 2),
    Zerg_Flyer_Carapace_3(134 ,bwapi.UpgradeType.Zerg_Flyer_Carapace, 3),
    Protoss_Ground_Armor_1(135 ,bwapi.UpgradeType.Protoss_Ground_Armor, 1),
    Protoss_Ground_Armor_2(136 ,bwapi.UpgradeType.Protoss_Ground_Armor, 2),
    Protoss_Ground_Armor_3(137 ,bwapi.UpgradeType.Protoss_Ground_Armor, 3),
    Protoss_Air_Armor_1(138 ,bwapi.UpgradeType.Protoss_Air_Armor, 1),
    Protoss_Air_Armor_2(139 ,bwapi.UpgradeType.Protoss_Air_Armor, 2),
    Protoss_Air_Armor_3(140 ,bwapi.UpgradeType.Protoss_Air_Armor, 3),
    Terran_Infantry_Weapons_1(141 ,bwapi.UpgradeType.Terran_Infantry_Weapons, 1),
    Terran_Infantry_Weapons_2(142 ,bwapi.UpgradeType.Terran_Infantry_Weapons, 2),
    Terran_Infantry_Weapons_3(143 ,bwapi.UpgradeType.Terran_Infantry_Weapons, 3),
    Terran_Vehicle_Weapons_1(144 ,bwapi.UpgradeType.Terran_Vehicle_Weapons, 1),
    Terran_Vehicle_Weapons_2(145 ,bwapi.UpgradeType.Terran_Vehicle_Weapons, 2),
    Terran_Vehicle_Weapons_3(146 ,bwapi.UpgradeType.Terran_Vehicle_Weapons, 3),
    Terran_Ship_Weapons_1(147 ,bwapi.UpgradeType.Terran_Ship_Weapons, 1),
    Terran_Ship_Weapons_2(148 ,bwapi.UpgradeType.Terran_Ship_Weapons, 2),
    Terran_Ship_Weapons_3(149 ,bwapi.UpgradeType.Terran_Ship_Weapons, 3),
    Zerg_Melee_Attacks_1(150 ,bwapi.UpgradeType.Zerg_Melee_Attacks, 1),
    Zerg_Melee_Attacks_2(151 ,bwapi.UpgradeType.Zerg_Melee_Attacks, 2),
    Zerg_Melee_Attacks_3(152 ,bwapi.UpgradeType.Zerg_Melee_Attacks, 3),
    Zerg_Missile_Attacks_1(153 ,bwapi.UpgradeType.Zerg_Missile_Attacks, 1),
    Zerg_Missile_Attacks_2(154 ,bwapi.UpgradeType.Zerg_Missile_Attacks, 2),
    Zerg_Missile_Attacks_3(155 ,bwapi.UpgradeType.Zerg_Missile_Attacks, 3),
    Zerg_Flyer_Attacks_1(156 ,bwapi.UpgradeType.Zerg_Flyer_Attacks, 1),
    Zerg_Flyer_Attacks_2(157 ,bwapi.UpgradeType.Zerg_Flyer_Attacks, 2),
    Zerg_Flyer_Attacks_3(158 ,bwapi.UpgradeType.Zerg_Flyer_Attacks, 3),
    Protoss_Ground_Weapons_1(159 ,bwapi.UpgradeType.Protoss_Ground_Weapons, 1),
    Protoss_Ground_Weapons_2(160 ,bwapi.UpgradeType.Protoss_Ground_Weapons, 2),
    Protoss_Ground_Weapons_3(161 ,bwapi.UpgradeType.Protoss_Ground_Weapons, 3),
    Protoss_Air_Weapons_1(162 ,bwapi.UpgradeType.Protoss_Air_Weapons, 1),
    Protoss_Air_Weapons_2(163 ,bwapi.UpgradeType.Protoss_Air_Weapons, 2),
    Protoss_Air_Weapons_3(164 ,bwapi.UpgradeType.Protoss_Air_Weapons, 3),
    Protoss_Plasma_Shields_1(165 ,bwapi.UpgradeType.Protoss_Plasma_Shields, 1),
    Protoss_Plasma_Shields_2(166 ,bwapi.UpgradeType.Protoss_Plasma_Shields, 2),
    Protoss_Plasma_Shields_3(167 ,bwapi.UpgradeType.Protoss_Plasma_Shields, 3),
    U_238_Shells(168 ,bwapi.UpgradeType.U_238_Shells),
    Ion_Thrusters(169 ,bwapi.UpgradeType.Ion_Thrusters),
    Titan_Reactor(170 ,bwapi.UpgradeType.Titan_Reactor),
    Ocular_Implants(171 ,bwapi.UpgradeType.Ocular_Implants),
    Moebius_Reactor(172 ,bwapi.UpgradeType.Moebius_Reactor),
    Apollo_Reactor(173 ,bwapi.UpgradeType.Apollo_Reactor),
    Colossus_Reactor(174 ,bwapi.UpgradeType.Colossus_Reactor),
    Ventral_Sacs(175 ,bwapi.UpgradeType.Ventral_Sacs),
    Antennae(176 ,bwapi.UpgradeType.Antennae),
    Pneumatized_Carapace(177 ,bwapi.UpgradeType.Pneumatized_Carapace),
    Metabolic_Boost(178 ,bwapi.UpgradeType.Metabolic_Boost),
    Adrenal_Glands(179 ,bwapi.UpgradeType.Adrenal_Glands),
    Muscular_Augments(180 ,bwapi.UpgradeType.Muscular_Augments),
    Grooved_Spines(181 ,bwapi.UpgradeType.Grooved_Spines),
    Gamete_Meiosis(182 ,bwapi.UpgradeType.Gamete_Meiosis),
    Metasynaptic_Node(183 ,bwapi.UpgradeType.Metasynaptic_Node),
    Singularity_Charge(184 ,bwapi.UpgradeType.Singularity_Charge),
    Leg_Enhancements(185 ,bwapi.UpgradeType.Leg_Enhancements),
    Scarab_Damage(186 ,bwapi.UpgradeType.Scarab_Damage),
    Reaver_Capacity(187 ,bwapi.UpgradeType.Reaver_Capacity),
    Gravitic_Drive(188 ,bwapi.UpgradeType.Gravitic_Drive),
    Sensor_Array(189 ,bwapi.UpgradeType.Sensor_Array),
    Gravitic_Boosters(190 ,bwapi.UpgradeType.Gravitic_Boosters),
    Khaydarin_Amulet(191 ,bwapi.UpgradeType.Khaydarin_Amulet),
    Apial_Sensors(192 ,bwapi.UpgradeType.Apial_Sensors),
    Gravitic_Thrusters(193 ,bwapi.UpgradeType.Gravitic_Thrusters),
    Carrier_Capacity(194 ,bwapi.UpgradeType.Carrier_Capacity),
    Khaydarin_Core(195 ,bwapi.UpgradeType.Khaydarin_Core),
    Argus_Jewel(196 ,bwapi.UpgradeType.Argus_Jewel),
    Argus_Talisman(197 ,bwapi.UpgradeType.Argus_Talisman),
    Caduceus_Reactor(198 ,bwapi.UpgradeType.Caduceus_Reactor),
    Chitinous_Plating(199 ,bwapi.UpgradeType.Chitinous_Plating),
    Anabolic_Synthesis(200 ,bwapi.UpgradeType.Anabolic_Synthesis),
    Charon_Boosters(201 ,bwapi.UpgradeType.Charon_Boosters),
    None(202, UnitType.None);

    final int id;
    final String name;
    final Race race;

    BuildType(int id, UnitType type)
    {
        this.id = id;
        this.name = type.toString();
        this.race = type.getRace();
    }
    
    BuildType(int id, TechType type)
    {
        this.id = id;
        this.name = type.toString();
        this.race = type.getRace();
    }

    BuildType(int id, UpgradeType type)
    {
        this(id, type, 1);
    }

    BuildType(int id, UpgradeType type, int level )
    {
        this.id = id;

        final String UpgradeLevelDelimiter = "_";
        this.name = String.format("%s%s%d", type.toString(), UpgradeLevelDelimiter, level);

        this.race = type.getRace();
    }

    public int getID() {
        return id;
    }

    public String getName() {
        return UnitType.AllUnits.toString();
    }

    public Race getRace() {
        return race;
    }

    /*
    boolean isTechType();

    boolean isUnitType();

    boolean isUpgradeType();

    TechType getTechType();

    UnitType getUnitType();

    UpgradeType getUpgradeType();

    int getUpgradeLevel();

    int getMask();

    int getRequiredMask();

    *//*
        const std::pair< BuildType, int >& whatBuilds();
        const std::map< BuildType, int >& requiredBuildTypes();
    *//*

    boolean requiresPsi();

    boolean requiresLarva();

    BuildType requiredAddon();

    int mineralPrice();

    int gasPrice();

    int builderTime();

    int buildUnitTime();

    int prepTime();

    boolean createsUnit();

    boolean morphsBuilder();

    boolean needsBuildLocation();

    int supplyRequired();

    int supplyProvided();*/
}
