"use client";

import { useRouter, useSearchParams } from "next/navigation";

const LEVELS = ["débutant", "intermédiaire", "avancé", "expert"];
const AVAILABILITIES = ["matin", "midi", "soir", "weekend"];

export default function PlayerFilter() {
  const router = useRouter();
  const searchParams = useSearchParams();

  function update(key: string, value: string) {
    const params = new URLSearchParams(searchParams.toString());
    if (params.get(key) === value) {
      params.delete(key);
    } else {
      params.set(key, value);
    }
    router.push(`/joueurs?${params.toString()}`);
  }

  function reset() {
    router.push("/joueurs");
  }

  const currentLevel = searchParams.get("level");
  const currentAvail = searchParams.get("availability");

  return (
    <div className="bg-white rounded-xl p-4 border border-gray-100 shadow-sm space-y-3">
      <div className="flex items-center justify-between">
        <span className="text-sm font-medium text-gray-700">Filtrer</span>
        {(currentLevel || currentAvail) && (
          <button onClick={reset} className="text-xs text-emerald-600 hover:underline">
            Réinitialiser
          </button>
        )}
      </div>

      <div>
        <p className="text-xs text-gray-500 mb-2">Niveau</p>
        <div className="flex flex-wrap gap-2">
          {LEVELS.map((l) => (
            <button
              key={l}
              onClick={() => update("level", l)}
              className={`text-xs px-3 py-1.5 rounded-full border transition-colors capitalize ${
                currentLevel === l
                  ? "bg-emerald-600 text-white border-emerald-600"
                  : "border-gray-200 text-gray-600 hover:border-emerald-300"
              }`}
            >
              {l}
            </button>
          ))}
        </div>
      </div>

      <div>
        <p className="text-xs text-gray-500 mb-2">Disponibilité</p>
        <div className="flex flex-wrap gap-2">
          {AVAILABILITIES.map((a) => (
            <button
              key={a}
              onClick={() => update("availability", a)}
              className={`text-xs px-3 py-1.5 rounded-full border transition-colors capitalize ${
                currentAvail === a
                  ? "bg-emerald-600 text-white border-emerald-600"
                  : "border-gray-200 text-gray-600 hover:border-emerald-300"
              }`}
            >
              {a}
            </button>
          ))}
        </div>
      </div>
    </div>
  );
}
