

(ns site.pages.main-page.frontend.sections.section-2)

;; -----------------------------------------------------------------------------
;; ---- Components ----

(def asd
  {:alcove "alcove"})

(defn title []
  [:p "BÉRELHETŐ JÁRMŰVEINK"])

(defn vehicle-type-button [{:keys [src]}]
  [:button
   [:img {:src src}]])

(defn filters [])


(defn section-2 []
  [:section
   [title]])

;; ---- Components ----
;; -----------------------------------------------------------------------------

(defn view []
  [section-2])
