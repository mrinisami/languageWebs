export const circleProgressColor = (score: number): string => {
  if (score === 0) {
    return "lightgrey   ";
  }
  if (score < 50) {
    return "#e74c3c";
  } else if (score < 75) {
    return "#f1c40f";
  }
  return "#27ae60";
};
