
(ns site.karavancentrum.components.link)

;; -----------------------------------------------------------------------------
;; -- Prototypes ---------------------------------------------------------------

(defn link-props-prototype [props text]
  (if-let [prefix (get props :prefix)]
    (merge (dissoc props prefix)
           {:href (str prefix text)})
    props))

;; -- Prototypes ---------------------------------------------------------------
;; -----------------------------------------------------------------------------

;; -----------------------------------------------------------------------------
;; -- Components ---------------------------------------------------------------

(defn link [props text]
  (let [link-props (link-props-prototype props text)]
    [:a.link.effect--underline link-props
     text]))

(defn view [{:keys [prefix] :as props} text]
  [link props text])
