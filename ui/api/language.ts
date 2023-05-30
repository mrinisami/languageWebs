export interface LanguageAllGrades {
  userGrade: number | null;
  evaluatorGrade: number | null;
  selfAssessment: number;
  language: string;
  translatedLanguage: string;
  id: number;
}

export interface LanguageGradeEmitter {
  grade: number;
  id: number;
  language: string;
  translatedLanguage: string;
}

export interface LanguagesGradesEmitter {
  languages: LanguageGradeEmitter[];
}

export interface LanguagesAllGrades {
  languages: LanguageAllGrades[];
}

export interface LanguageRequest {
  grade: number;
  refLanguage: string;
  translatedLanguage: string;
}

export const languageToFlag: Record<string, string> = {
  english: "https://hatscripts.github.io/circle-flags/flags/gb.svg",
  french: "https://hatscripts.github.io/circle-flags/flags/fr.svg",
  arabic: "https://hatscripts.github.io/circle-flags/flags/sa.svg",
  armenian: "https://hatscripts.github.io/circle-flags/flags/am.svg",
  albanian: "https://hatscripts.github.io/circle-flags/flags/al.svg",
  chinese: "https://hatscripts.github.io/circle-flags/flags/cn.svg",
  spanish: "https://hatscripts.github.io/circle-flags/flags/es.svg",
  hindi: "https://hatscripts.github.io/circle-flags/flags/in.svg",
  portuguese: "https://hatscripts.github.io/circle-flags/flags/pt.svg",
  bengali: "https://hatscripts.github.io/circle-flags/flags/bd.svg",
  russian: "https://hatscripts.github.io/circle-flags/flags/ru.svg",
  japanese: "https://hatscripts.github.io/circle-flags/flags/jp.svg",
  cantonese: "https://hatscripts.github.io/circle-flags/flags/cn.svg",
  vietnamese: "https://hatscripts.github.io/circle-flags/flags/vn.svg",
  turkish: "https://hatscripts.github.io/circle-flags/flags/tr.svg",
  marathis: "https://hatscripts.github.io/circle-flags/flags/in.svg",
  korean: "https://hatscripts.github.io/circle-flags/flags/kr.svg",
  tamil: "https://hatscripts.github.io/circle-flags/flags/lk.svg",
  german: "https://hatscripts.github.io/circle-flags/flags/de.svg",
  urdu: "https://hatscripts.github.io/circle-flags/flags/pk.svg",
  javanese: "https://hatscripts.github.io/circle-flags/flags/id.svg",
  indonesian: "https://hatscripts.github.io/circle-flags/flags/id.svg"
};
