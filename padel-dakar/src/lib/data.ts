export type Level = "débutant" | "intermédiaire" | "avancé" | "expert";
export type Availability = "matin" | "midi" | "soir" | "weekend";

export interface Player {
  id: string;
  name: string;
  age: number;
  level: Level;
  availabilities: Availability[];
  preferredCourts: string[];
  bio: string;
  avatar: string;
  matchesPlayed: number;
  wins: number;
  phone: string;
}

export interface Court {
  id: string;
  name: string;
  address: string;
  neighborhood: string;
  courts: number;
  pricePerHour: number;
  phone: string;
  open: string;
}

export interface Match {
  id: string;
  player1Id: string;
  player2Id: string;
  courtId: string;
  date: string;
  time: string;
  status: "proposé" | "confirmé" | "terminé" | "annulé";
  score?: string;
}

export const COURTS: Court[] = [
  {
    id: "c1",
    name: "Padel Club Almadies",
    address: "Route des Almadies, Dakar",
    neighborhood: "Almadies",
    courts: 4,
    pricePerHour: 15000,
    phone: "+221 77 000 0001",
    open: "07h - 22h",
  },
  {
    id: "c2",
    name: "Dakar Padel Center",
    address: "Cité Keur Gorgui, Dakar",
    neighborhood: "Keur Gorgui",
    courts: 2,
    pricePerHour: 12000,
    phone: "+221 77 000 0002",
    open: "08h - 21h",
  },
  {
    id: "c3",
    name: "Club Amitié Padel",
    address: "Avenue Cheikh Anta Diop, Dakar",
    neighborhood: "Amitié",
    courts: 3,
    pricePerHour: 10000,
    phone: "+221 77 000 0003",
    open: "07h - 22h",
  },
  {
    id: "c4",
    name: "Saly Padel Dakar",
    address: "Zone de Recasement, Guédiawaye",
    neighborhood: "Guédiawaye",
    courts: 2,
    pricePerHour: 8000,
    phone: "+221 77 000 0004",
    open: "08h - 20h",
  },
  {
    id: "c5",
    name: "King Padel Dakar",
    address: "Mermoz, Dakar",
    neighborhood: "Mermoz",
    courts: 3,
    pricePerHour: 13000,
    phone: "+221 77 000 0005",
    open: "07h - 22h",
  },
];

export const PLAYERS: Player[] = [
  {
    id: "p1",
    name: "Mamadou Diallo",
    age: 28,
    level: "intermédiaire",
    availabilities: ["soir", "weekend"],
    preferredCourts: ["c1", "c3"],
    bio: "Passionné de padel depuis 2 ans, je cherche des partenaires pour progresser ensemble.",
    avatar: "MD",
    matchesPlayed: 34,
    wins: 18,
    phone: "+221 77 123 4567",
  },
  {
    id: "p2",
    name: "Fatou Sow",
    age: 25,
    level: "débutant",
    availabilities: ["matin", "weekend"],
    preferredCourts: ["c2"],
    bio: "Débutante motivée ! Je joue depuis 6 mois et cherche des partenaires patients.",
    avatar: "FS",
    matchesPlayed: 12,
    wins: 4,
    phone: "+221 76 234 5678",
  },
  {
    id: "p3",
    name: "Ibrahima Ndiaye",
    age: 35,
    level: "avancé",
    availabilities: ["matin", "soir"],
    preferredCourts: ["c1", "c5"],
    bio: "Ex-tennisman reconverti au padel il y a 3 ans. Joueur compétitif, open à tous niveaux.",
    avatar: "IN",
    matchesPlayed: 89,
    wins: 61,
    phone: "+221 70 345 6789",
  },
  {
    id: "p4",
    name: "Aïssatou Bâ",
    age: 30,
    level: "intermédiaire",
    availabilities: ["midi", "soir", "weekend"],
    preferredCourts: ["c3", "c4"],
    bio: "Je joue 3x par semaine, cherche des partenaires réguliers pour des sessions intenses.",
    avatar: "AB",
    matchesPlayed: 52,
    wins: 29,
    phone: "+221 78 456 7890",
  },
  {
    id: "p5",
    name: "Ousmane Thiaw",
    age: 22,
    level: "expert",
    availabilities: ["matin", "midi", "soir"],
    preferredCourts: ["c1", "c5"],
    bio: "Niveau compétition nationale. Cherche adversaires de haut niveau pour m'améliorer.",
    avatar: "OT",
    matchesPlayed: 145,
    wins: 112,
    phone: "+221 77 567 8901",
  },
  {
    id: "p6",
    name: "Rokhaya Kane",
    age: 27,
    level: "débutant",
    availabilities: ["weekend"],
    preferredCourts: ["c2", "c4"],
    bio: "Nouvelle à Dakar, je cherche à découvrir le padel et rencontrer du monde !",
    avatar: "RK",
    matchesPlayed: 5,
    wins: 1,
    phone: "+221 76 678 9012",
  },
  {
    id: "p7",
    name: "Cheikh Mbacké",
    age: 40,
    level: "intermédiaire",
    availabilities: ["matin", "weekend"],
    preferredCourts: ["c3"],
    bio: "Père de famille, je joue le matin avant le travail. Sérieux et ponctuel.",
    avatar: "CM",
    matchesPlayed: 28,
    wins: 14,
    phone: "+221 70 789 0123",
  },
  {
    id: "p8",
    name: "Ndéye Diop",
    age: 32,
    level: "avancé",
    availabilities: ["soir", "weekend"],
    preferredCourts: ["c1", "c3", "c5"],
    bio: "Compétitrice dans l'âme. Cherche des partenaires pour des matchs réguliers.",
    avatar: "ND",
    matchesPlayed: 76,
    wins: 48,
    phone: "+221 78 890 1234",
  },
];

export const MATCHES: Match[] = [
  {
    id: "m1",
    player1Id: "p1",
    player2Id: "p3",
    courtId: "c1",
    date: "2026-07-18",
    time: "18:00",
    status: "confirmé",
  },
  {
    id: "m2",
    player1Id: "p2",
    player2Id: "p6",
    courtId: "c2",
    date: "2026-07-19",
    time: "10:00",
    status: "proposé",
  },
  {
    id: "m3",
    player1Id: "p4",
    player2Id: "p8",
    courtId: "c3",
    date: "2026-07-15",
    time: "19:00",
    status: "terminé",
    score: "6-3 / 4-6 / 6-4",
  },
];

export const LEVEL_COLORS: Record<Level, string> = {
  débutant: "bg-green-100 text-green-800",
  intermédiaire: "bg-blue-100 text-blue-800",
  avancé: "bg-orange-100 text-orange-800",
  expert: "bg-red-100 text-red-800",
};

export const AVAILABILITY_LABELS: Record<Availability, string> = {
  matin: "Matin (7h-12h)",
  midi: "Midi (12h-15h)",
  soir: "Soir (17h-21h)",
  weekend: "Weekend",
};
