(ns plugins.text-editor.view
  (:require
   ["react" :as react :default useMemo]
   [reagent.core :as r :refer [atom]]
   [jodit-react :default JoditEditor]
   [x.app-core.api :as a]
   [x.app-elements.api :as elements]))

;; -----------------------------------------------------------------------------
;; ---- Configurations ----

(def default-config {:language "hu"
                     :minHeight "400"
                     :cleanHTML true
                     :cleanWhitespace true})

;; ---- Configurations ----
;; -----------------------------------------------------------------------------

;; -----------------------------------------------------------------------------
;; ---- Components ----


(defn text-editor [{:keys [config value-path init-value ditor-props] :as props}]
  (let [config  (clj->js (if config config default-config))]
    [:> JoditEditor
      {:config   config
       :value    init-value
       :onChange (fn [value]
                   (a/dispatch-sync [:db/set-item! value-path value]))
       :onBlur   (fn [value]
                   (a/dispatch-sync [:db/set-item! value-path value]))}]))

(defn view [{:keys [value-path settings]}]
  ;  @param {:value-path   (keywords in vector)
  ;          :settings     (hash-map)(opt)                                      ;; optional settings if not included default-settings going to use
  ;          :editor-props (hash-map)(opt)}]                                    ;; optional props for JoditEditor in case have to overwrite something
  ;
  ; @usage [plugins/text-editor {:value-path [:my-value-path]})
  ;
  ; @return [reagent-component]}
  (assert value-path
          (str "Must provide :value-path in text-editor config"))
  (let [
        init-value (or @(a/subscribe [:db/get-item value-path]) "")]            ;; check reframe-db for value if nil return string to jodit
    (fn [config]
      (let [editor-settings (assoc config :init-value init-value)]
         [:f> text-editor editor-settings]))))
